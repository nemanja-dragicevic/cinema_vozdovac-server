package com.master.BioskopVozdovac.hall.model;

import com.master.BioskopVozdovac.seat.model.SeatDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Builder
public record HallDTO(Long hallID,
                      @NotBlank String hallName,
                      @Positive int rowsCount,
                      @Positive int seatsPerRow,
                      @NotEmpty List<SeatDTO> seats) {
}
