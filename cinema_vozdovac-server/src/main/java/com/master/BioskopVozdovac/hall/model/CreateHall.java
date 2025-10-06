package com.master.BioskopVozdovac.hall.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateHall(@NotBlank String hallName,
                         @Positive int rowsCount,
                         @Positive int seatsPerRow) {

}
