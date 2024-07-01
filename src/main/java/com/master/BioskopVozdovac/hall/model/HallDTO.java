package com.master.BioskopVozdovac.hall.model;

import com.master.BioskopVozdovac.seat.model.SeatDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class HallDTO {

    private Long hallID;

    private String hallName;

    private int rowsCount;

    private int seatsPerRow;

    private List<SeatDTO> seats;

}
