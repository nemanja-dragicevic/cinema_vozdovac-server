package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProjectDTO {

    private Long id;
    @NotNull private MovieDTO movie;
    @NotNull private HallDTO hall;
    @NotNull private LocalDateTime projectTime;
    @NotNull private LocalDateTime projectEnd;
    @NotNull @Positive private Long price;

}
