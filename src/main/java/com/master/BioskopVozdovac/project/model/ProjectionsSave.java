package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectionsSave {

    private Long id;
    private MovieDTO movie;
    private HallDTO hall;
    private List<LocalTime> projectionTime;
    private int price;

}
