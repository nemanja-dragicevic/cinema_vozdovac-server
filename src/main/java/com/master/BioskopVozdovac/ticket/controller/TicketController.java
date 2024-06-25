package com.master.BioskopVozdovac.ticket.controller;

import com.master.BioskopVozdovac.stripe_config.StripeService;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    private final StripeService stripeService;

    @PostMapping("/create_payment")
    public ResponseEntity<StripeResponse> createPayment(@RequestBody TicketDTO dto) {
        StripeResponse response = stripeService.createPayment(dto);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

}
