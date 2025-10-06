package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;

import java.time.LocalDateTime;

public class TicketData {

    public static final TicketDTO TICKET_DTO = TicketDTO.builder()
            .id(1L)
            .memberID(1L)
            .payinTime(LocalDateTime.of(2025, 10, 1, 16, 12))
            .total(500L)
            .status(TicketStatus.PAID)
            .totalSeats(2)
            .build();

}
