package com.master.BioskopVozdovac.hall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHall {

    private HallDTO dto;

    private int numberOfRows;

    private int seatsPerRow;

}
