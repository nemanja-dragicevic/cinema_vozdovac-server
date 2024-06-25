package com.master.BioskopVozdovac.stripe_config;

import com.master.BioskopVozdovac.stripe_config.model.CapturePaymentResponse;
import com.master.BioskopVozdovac.stripe_config.model.CreatePaymentResponse;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String key;

    public StripeResponse createPayment(TicketDTO ticket) {
        // Set your secret key. Remember to switch to your live secret key in production!
        Stripe.apiKey = key;

        // Create a PaymentIntent with the order amount and currency
        String name = "Bioskop Vozdovac";
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(name)
                        .build();

        // Create new line item with the above product data and associated price
        String currency = "rsd";
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(currency)
                        .setUnitAmount(ticket.getTotal() * 100)
                        .setProductData(productData)
                        .build();

        // Create new line item with the above price data
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        // Create new session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3000")
                        .setCancelUrl("http://localhost:3000")
                        .addLineItem(lineItem)
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

    public StripeResponse capturePayment(String sessionId) {
        Stripe.apiKey = key;

        try {
            Session session = Session.retrieve(sessionId);
            String status = session.getStatus();

            if (status.equalsIgnoreCase(Constant.STRIPE_SESSION_STATUS_SUCCESS)) {
                // Handle logic for successful payment
                log.info("Payment successfully captured.");
            }

            CapturePaymentResponse responseData = CapturePaymentResponse
                    .builder()
                    .sessionId(sessionId)
                    .sessionStatus(status)
                    .paymentStatus(session.getPaymentStatus())
                    .build();

            return StripeResponse
                    .builder()
                    .status(Constant.SUCCESS)
                    .message("Payment successfully captured.")
                    .httpStatus(200)
                    .data(responseData)
                    .build();
        } catch (StripeException e) {
            // Handle capture failure, log the error, and return false
            e.printStackTrace();
            return StripeResponse
                    .builder()
                    .status(Constant.FAILURE)
                    .message("Payment capture failed due to a server error.")
                    .httpStatus(500)
                    .data(null)
                    .build();
        }
    }

}
