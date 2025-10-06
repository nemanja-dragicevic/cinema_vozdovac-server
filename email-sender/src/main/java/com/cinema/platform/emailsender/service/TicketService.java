package com.cinema.platform.emailsender.service;

import com.cinema.platform.emailsender.config.EmailConfig;
import com.cinema_platform.avro.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final EmailConfig emailConfig;

    public void sendConfirmationEmail(Ticket ticket) {
        emailConfig.sendConfirmationEmail(ticket);
    }

}
