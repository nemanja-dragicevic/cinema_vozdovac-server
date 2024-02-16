package com.master.BioskopVozdovac.broadcast.adapter;

import com.master.BioskopVozdovac.broadcast.model.BroadcastDTO;
import com.master.BioskopVozdovac.broadcast.model.BroadcastEntity;
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
public class BroadcastAdapter {

    private final MovieAdapter movieAdapter;
    private final HallAdapter hallAdapter;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public BroadcastDTO entityToDTO(final BroadcastEntity entity) {
        if (entity == null)
            return null;

        final BroadcastDTO dto = new BroadcastDTO();

        dto.setId(entity.getBroadcastID());
        dto.setMovie(movieAdapter.entityToDTO(entity.getMovie()));
        dto.setHall(hallAdapter.entityToDTO(entity.getHall()));
        dto.setBroadcastDateTime(entity.getBroadcastDateTime());

        return dto;
    }

    public BroadcastEntity dtoToEntity(final BroadcastDTO dto) {
        if (dto == null)
            return null;

        final BroadcastEntity entity = new BroadcastEntity();

        entity.setBroadcastID(dto.getId());
        entity.setMovie(prepareMovie(dto.getMovie()));
        entity.setHall(prepareHall(dto.getHall()));
        entity.setBroadcastDateTime(dto.getBroadcastDateTime());

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
