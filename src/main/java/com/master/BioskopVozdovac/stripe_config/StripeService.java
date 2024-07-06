package com.master.BioskopVozdovac.stripe_config;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.exception.ExceptionResponse;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import com.master.BioskopVozdovac.stripe_config.model.CreatePaymentResponse;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemDTO;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String key;

    private final ProjectRepository projectRepository;

    private final TicketAdapter ticketAdapter;

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
            ticketRepository.save(entity);

            return ticket;
        } catch (StripeException e) {
            // Handle capture failure, log the error, and return false
            e.printStackTrace();
            throw new ExceptionResponse("Payment capture failed", e.getMessage());
        }
    }

}
