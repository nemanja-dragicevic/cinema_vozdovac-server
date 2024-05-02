package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.hall.model.HallProjections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieProject {

    private MovieDTO movie;

    private List<HallProjections> projections;

}
