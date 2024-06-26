package com.master.BioskopVozdovac.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketItemDTO {

    private Long id;
    private Long projectId;
    private Long seatId;

}
