package com.master.BioskopVozdovac.seat.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SeatDTO(Long id,
                      @NotNull Long hallID,
                      @Positive int rowNumber,
                      @Positive int seatNumber) {
}
