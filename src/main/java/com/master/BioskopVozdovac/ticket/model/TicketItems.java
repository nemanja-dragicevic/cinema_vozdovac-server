package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.seat.model.SeatDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketItems {

    private ProjectDTO projectDTO;
    private SeatDTO seatDTO;

}
