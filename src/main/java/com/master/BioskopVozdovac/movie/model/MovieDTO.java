package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long movieID;

    private String name;

    private LocalDate startTime;

    private String base64Image;

    private String description;

    private String duration;

    private Set<GenreDTO> genres;

}
