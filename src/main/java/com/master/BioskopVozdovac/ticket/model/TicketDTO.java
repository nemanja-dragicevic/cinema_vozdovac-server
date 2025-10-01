package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {

    private Long id;
    @NotNull @Positive private Long memberID;
    private LocalDateTime payinTime;
    @NotNull @Positive private Long total;
    private TicketStatus status;
    @Positive private int totalSeats;
    private Set<TicketItemDTO> ticketItems = new HashSet<>();

}
