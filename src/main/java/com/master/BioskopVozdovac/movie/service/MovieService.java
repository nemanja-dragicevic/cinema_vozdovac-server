package com.master.BioskopVozdovac.movie.service;

import com.master.BioskopVozdovac.movie.adapter.MovieAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieAdapter movieAdapter;

    public MovieDTO saveMovie(MovieDTO dto) {
        MovieEntity entity = movieRepository.save(movieAdapter.dtoToEntity(dto));
        return movieAdapter.entityToDTO(entity);
    }

    public MovieDTO getMovieById(Long id) {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(()
                    -> new NoSuchElementException("No element with id: " + id));
        return movieAdapter.entityToDTO(entity);
    }

    public MovieDTO updateMovie(MovieDTO dto) {
        MovieEntity entity = movieAdapter.dtoToEntity(dto);
        return movieAdapter.entityToDTO(movieRepository.saveAndFlush(entity));
    }

    public String deleteMovieById(Long id) {
        movieRepository.deleteById(id);
        return "Successfully deleted movie with id: " + id;
    }

    public List<MovieDTO> getAllMovies() {
        return movieAdapter.toDto(movieRepository.findAll());
    }
}
