package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.model.ProjectTimes;
import com.master.BioskopVozdovac.project.model.ProjectionsSave;
import com.master.BioskopVozdovac.role.model.RoleDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static com.master.BioskopVozdovac.input.GenreData.GENRE_DTO;
import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static com.master.BioskopVozdovac.input.HallData.HALL_ENTITY;

public class MovieData {

    public static final RoleDTO ROLE_DTO = new RoleDTO(1L, 1L, 1L, "Main actor");

    public static final MovieEntity MOVIE_ENTITY = MovieEntity.create(1L, "John Wick",
            "Keanu Reeves looks for revenge", 120, LocalDate.of(2025, 9, 11),
            LocalDate.of(2025, 9, 13));

    public static final MovieDTO MOVIE_DTO = MovieDTO.builder()
            .movieID(1L)
            .name("John Wick")
            .startTime(LocalDate.of(2025, 9, 11))
            .endTime(LocalDate.of(2025, 9, 13))
            .description("Keanu Reeves looks for revenge")
            .duration(120)
            .genres(Set.of(GENRE_DTO))
            .build();

    public static final ProjectDTO PROJECT_DTO = ProjectDTO.builder()
            .id(1L)
            .movie(MOVIE_DTO)
            .hall(HALL_DTO)
            .projectTime(LocalDateTime.of(2025, 10, 11, 15, 0))
            .projectEnd(LocalDateTime.of(2025, 10, 11, 17, 0))
            .price(1000L)
            .build();

    public static final ProjectEntity PROJECT_ENTITY = ProjectEntity.builder()
            .projectID(1L)
            .movie(MOVIE_ENTITY)
            .hall(HALL_ENTITY)
            .time(LocalDateTime.of(2025, 10, 11, 15, 0))
            .projectEnd(LocalDateTime.of(2025, 10, 11, 17, 0))
            .price(1000L)
            .build();

    public static final ProjectTimes PROJECT_TIMES = new ProjectTimes(
            LocalTime.of(15, 0),
            LocalTime.of(17, 0));

    public static final ProjectionsSave PROJECTIONS_SAVE = new ProjectionsSave(1L, MOVIE_DTO, HALL_DTO,
            List.of(LocalTime.of(15, 0)), 1000L);
}
