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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectAdapter {

    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final MovieAdapter movieAdapter;

    public ProjectDTO entityToDTO(final ProjectEntity entity) {
        if (entity == null)
            return null;

        final ProjectDTO dto = new ProjectDTO();

        dto.setId(entity.getProjectID());
        dto.setMovie(movieAdapter.entityToDTO(entity.getMovie()));
        dto.setHall(HallAdapter.entityToDTO(entity.getHall()));
        dto.setProjectTime(entity.getTime());
        dto.setProjectEnd(entity.getProjectEnd());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public ProjectEntity dtoToEntity(final ProjectDTO dto) {
        if (dto == null)
            return null;

        final ProjectEntity entity = new ProjectEntity();

        entity.setProjectID(dto.getId());
        entity.setMovie(prepareMovie(dto.getMovie()));
        entity.setHall(prepareHall(dto.getHall()));
        entity.setTime(dto.getProjectTime());
        entity.setProjectEnd(dto.getProjectEnd());
        entity.setPrice(dto.getPrice());

        return entity;
    }

    public List<ProjectDTO> toDTOs(final List<ProjectEntity> entities) {
        if (entities == null)
            return null;

        return entities.stream().map(this::entityToDTO).toList();
    }

    private MovieEntity prepareMovie(MovieDTO movie) {
        if (movie == null || movie.getMovieID() == null)
            return null;

        return movieRepository.findById(movie.getMovieID()).orElseThrow(() ->
                new NoSuchElementException("No movie with id: " + movie.getMovieID()));
    }

    private HallEntity prepareHall(HallDTO hall) {
        if (hall == null || hall.hallID() == null)
            return null;

        return hallRepository.findById(hall.hallID()).orElseThrow(() ->
                new NoSuchElementException("No hall with id: " + hall.hallID()));
    }

}
