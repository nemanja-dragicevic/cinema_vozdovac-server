package com.master.BioskopVozdovac.ticket.controller;

import com.master.BioskopVozdovac.stripe_config.StripeService;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.model.SessionParam;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketItems;
import com.master.BioskopVozdovac.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling ticket-related operations via REST API endpoints.
 *
 * @author Nemanja Dragićević
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/ticket")
public class TicketController {

    /**
     * Service for interacting with Stripe API
     */
    private final StripeService stripeService;

    /**
     * Service for ticket-related business logic
     */
    private final TicketService ticketService;

    /**
     * Endpoint for initiating a payment creation process using Stripe.
     * This will return a link that will redirect a member to check out page
     *
     * @param dto The TicketDTO containing payment details.
     * @return ResponseEntity with StripeResponse containing payment creation status.
     */
    @PostMapping("/create_payment")
    public ResponseEntity<StripeResponse> createPayment(@RequestBody TicketDTO dto) {
        StripeResponse response = stripeService.createPayment(dto);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    /**
     * Endpoint for capturing a payment using Stripe session ID.
     *
     * @param param The SessionParam containing the Stripe session ID.
     * @return ResponseEntity with TicketDTO containing captured payment details.
     */
    @PostMapping("/capture_payment")
    public ResponseEntity<TicketDTO> capturePayment(@RequestBody SessionParam param) {
        TicketDTO response = stripeService.capturePayment(param.getSessionId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TicketDTO>> getTicketHistory(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.getTicketsUser(id), HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TicketItems>> getTicketItems(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.getTicketItems(id), HttpStatus.OK);
    }

    /**
     * Endpoint for initiating a refund of a payment.
     *
     * @param id The ID of the ticket to be refunded.
     * @return ResponseEntity with a success message indicating the refund status.
     */
    @PostMapping("/refund/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> refundPayment(@PathVariable Long id) {
        return new ResponseEntity<>(stripeService.refund(id), HttpStatus.OK);
    }

}
