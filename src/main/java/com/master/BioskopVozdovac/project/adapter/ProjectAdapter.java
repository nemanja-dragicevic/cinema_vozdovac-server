package com.master.BioskopVozdovac.project.adapter;

import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import com.master.BioskopVozdovac.movie.adapter.MovieAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class ProjectAdapter {

    private final MovieAdapter movieAdapter;
    private final HallAdapter hallAdapter;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public ProjectDTO entityToDTO(final ProjectEntity entity) {
        if (entity == null)
            return null;

        final ProjectDTO dto = new ProjectDTO();

        dto.setId(entity.getProjectID());
        dto.setMovieID(movieAdapter.entityToDTO(entity.getMovie()));
        dto.setHallID(hallAdapter.entityToDTO(entity.getHall()));

        return dto;
    }

    public ProjectEntity dtoToEntity(final ProjectDTO dto) {
        if (dto == null)
            return null;

        final ProjectEntity entity = new ProjectEntity();

        entity.setProjectID(dto.getId());
        entity.setMovie(prepareMovie(dto.getMovieID()));
        entity.setHall(prepareHall(dto.getHallID()));

        return entity;
    }

    private MovieEntity prepareMovie(MovieDTO movie) {
        if (movie == null || movie.getMovieID() == null)
            return null;

        return movieRepository.findById(movie.getMovieID()).orElseThrow(() ->
                new NoSuchElementException("No movie with id: " + movie.getMovieID()));
    }

    private HallEntity prepareHall(HallDTO hall) {
        if (hall == null || hall.getHallID() == null)
            return null;

        return hallRepository.findById(hall.getHallID()).orElseThrow(() ->
                new NoSuchElementException("No hall with id: " + hall.getHallID()));
    }

}
