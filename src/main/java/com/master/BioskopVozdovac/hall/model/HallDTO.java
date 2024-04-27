package com.master.BioskopVozdovac.hall.model;

import com.master.BioskopVozdovac.seat.model.SeatDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HallDTO {

    private Long hallID;

    private String hallName;

    private int hallCapacity;

    private List<SeatDTO> seats;

}
