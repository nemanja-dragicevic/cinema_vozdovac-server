package com.master.BioskopVozdovac.seat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {

    private Long id;

    private Long hallID;

    private int rowNumber;

    private int seatNumber;

}
