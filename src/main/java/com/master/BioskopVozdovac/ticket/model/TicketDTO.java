package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Long id;
    private Long memberID;
    private LocalDateTime payinTime;
    private Long total;
    private TicketStatus status;
    private int totalSeats;
    private Set<TicketItemDTO> ticketItems = new HashSet<>();

}
