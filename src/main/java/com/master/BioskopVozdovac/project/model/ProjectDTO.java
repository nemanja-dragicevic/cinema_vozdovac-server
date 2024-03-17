package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDTO {

    private Long id;
    private MovieDTO movieID;
    private HallDTO hallID;
    private LocalDateTime projectTime;

}
