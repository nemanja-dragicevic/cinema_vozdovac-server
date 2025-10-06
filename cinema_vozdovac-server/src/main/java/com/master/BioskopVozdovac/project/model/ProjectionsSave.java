package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;
import java.util.List;

public record ProjectionsSave(Long id,
                              @NotNull MovieDTO movie,
                              @NotNull HallDTO hall,
                              @NotEmpty List<LocalTime> projectionTime,
                              @Positive Long price) {
}
