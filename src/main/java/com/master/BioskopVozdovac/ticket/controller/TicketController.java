package com.master.BioskopVozdovac.ticket.controller;

import com.master.BioskopVozdovac.stripe_config.StripeService;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/ticket")
public class TicketController {

    private final StripeService stripeService;

    @PostMapping("/create_payment")
    public ResponseEntity<StripeResponse> createPayment(@RequestBody TicketDTO dto) {
        StripeResponse response = stripeService.createPayment(dto);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PostMapping("/capture_payment")
    public ResponseEntity<TicketDTO> capturePayment(@RequestParam String sessionId) {
        TicketDTO response = stripeService.capturePayment(sessionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
