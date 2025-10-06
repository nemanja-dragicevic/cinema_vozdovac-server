package com.master.BioskopVozdovac.stripe_config;

import com.cinema_platform.avro.Ticket;
import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.exception.ExceptionResponse;
import com.master.BioskopVozdovac.kafka.ConfirmationProducer;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import com.master.BioskopVozdovac.stripe_config.model.CreatePaymentResponse;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.adapter.TicketAvroAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemDTO;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for interacting with Stripe API to handle payment operations.
 *
 * @author Nemanja Dragićević
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StripeService {

    /**
     * Stripe API key for authentication.
     */
    @Value("${STRIPE_SECRET_KEY}")
    private String key;

    /**
     * Repository for managing projection entities in the database.
     */
    private final ProjectRepository projectRepository;

    /**
     * Kafka Producer for sending confirmation emails
     */
    private final ConfirmationProducer confirmationProducer;

    /**
     * Adapter for converting between DTOs and entities related to tickets.
     */
    private final TicketAdapter ticketAdapter;

    /**
     * Repository for managing ticket entities in the database.
     */
    private final TicketRepository ticketRepository;

    public StripeResponse createPayment(TicketDTO ticket) {
        Stripe.apiKey = key;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        Map<String, String> metadata = new HashMap<>();
        long amount = 0;

        for(TicketItemDTO item : ticket.getTicketItems()) {
            String currency = "rsd";
            ProjectEntity project = projectRepository.findById(item.getProjectionId()).orElseThrow(
                    () -> new RuntimeException("Projection not found")
            );
            amount += project.getPrice();
            // Create a PaymentIntent with the order amount and currency
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(project.getMovie().getName())
                            .build();

            // Create new line item with the above product data and associated price
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(currency)
                            .setUnitAmount(project.getPrice() * 100)
                            .setProductData(productData)
                            .build();

            // Create new line item with the above price data
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams
                            .LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(priceData)
                            .build();

            lineItems.add(lineItem);

            metadata.put("item_" + item.getSeatId() + item.getProjectionId(), item.getProjectionId() + "," + item.getSeatId());
        }

        metadata.put("memberID", ticket.getMemberID().toString());
        metadata.put("total", Long.toString(amount));
        metadata.put("totalSeats", Integer.toString(lineItems.size()));

        // Create new session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3000/success")
                        .setCancelUrl("http://localhost:3000/failure")
                        .addAllLineItem(lineItems)
                        .putAllMetadata(metadata)
                        .build();

        // Create new session
        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            return StripeResponse
                    .builder()
                    .status(Constant.FAILURE)
                    .message("Payment session creation failed")
                    .httpStatus(400)
                    .data(null)
                    .build();
        }

        CreatePaymentResponse responseData = CreatePaymentResponse
                .builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();

        return StripeResponse
                .builder()
                .status(Constant.SUCCESS)
                .message("Payment session created successfully")
                .httpStatus(200)
                .data(responseData)
                .build();
    }

    /**
     * Captures a payment using the provided Stripe session ID.
     *
     * @param sessionId The Stripe session ID used to retrieve payment details.
     * @return TicketDTO representing the captured payment details.
     * @throws ExceptionResponse if there is a failure in capturing the payment.
     */
    public TicketDTO capturePayment(String sessionId) {
        Stripe.apiKey = key;

        try {
            Session session = Session.retrieve(sessionId);
            String status = session.getStatus();

            if (status.equalsIgnoreCase(Constant.STRIPE_SESSION_STATUS_SUCCESS)) {
                // Handle logic for successful payment
                log.info("Payment successfully captured.");
            }

            Map<String, String> metadata = session.getMetadata();

            TicketDTO ticket = new TicketDTO();
            ticket.setMemberID(Long.parseLong(metadata.get("memberID")));
            ticket.setTotalSeats(Integer.parseInt(metadata.get("totalSeats")));
            ticket.setStatus(TicketStatus.PAID);
            ticket.setPayinTime(LocalDateTime.now());
            ticket.setTotal(Long.parseLong(metadata.get("total")));

            for(Map.Entry<String, String> entry : metadata.entrySet()) {
                if (entry.getKey().startsWith("item_")) {
                    String[] itemData = entry.getValue().split(",");

                    TicketItemDTO ticketItem = new TicketItemDTO();
                    ticketItem.setProjectionId(Long.parseLong(itemData[0]));
                    ticketItem.setSeatId(Long.parseLong(itemData[1]));

                    ticket.getTicketItems().add(ticketItem);
                }
            }

            TicketEntity entity = ticketAdapter.dtoToEntity(ticket);
            entity.setPaymentIntent(session.getPaymentIntent());
            ticketRepository.save(entity);

            final Ticket ticketAvro = TicketAvroAdapter.entityToAvro(entity);
            confirmationProducer.sendConfirmation(ticketAvro);

            return ticket;
        } catch (StripeException e) {
            // Handle capture failure, log the error, and return false
            log.error(e.getMessage());
            throw new ExceptionResponse("Payment capture failed", e.getMessage());
        }
    }



    /**
     * Initiates a refund for a ticket identified by the provided ID.
     *
     * This method performs the following steps:
     * 1. Retrieves the ticket entity from the database using the given ID.
     * 2. Sets up parameters for creating a refund using the Stripe API based on the ticket's payment intent.
     * 3. Calls the Stripe API to create the refund.
     * 4. Updates the status of the ticket entity to {@link TicketStatus#REVOKE}.
     * 5. Saves the updated ticket entity back to the database.
     * 6. Converts the updated ticket entity to a DTO (Data Transfer Object) for returning as part of the response.
     *
     * @param id The ID of the ticket to refund.
     * @return A {@link TicketDTO} representing the updated ticket after refund.
     * @throws RuntimeException If there is an error processing the refund, either due to Stripe API errors
     *                          (wrapped as {@link StripeException}) or other general exceptions.
     * @throws ExceptionResponse If no ticket is found with the given ID in the database.
     */
    public TicketDTO refund(Long id) {
        try {
            Stripe.apiKey = key;

            TicketEntity entity = ticketRepository.findById(id).orElseThrow(
                    () -> new ExceptionResponse("There is no such ticket", "Please check your ticket id")
            );
            RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(entity.getPaymentIntent()).build();

            Refund refund = Refund.create(params);

            entity.setStatus(TicketStatus.REVOKE);
            ticketRepository.saveAndFlush(entity);
            return ticketAdapter.entityToDTO(entity);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // General exception handling
            throw new RuntimeException("Failed to process refund for ticket with id " + id, e);
        }
    }
}
