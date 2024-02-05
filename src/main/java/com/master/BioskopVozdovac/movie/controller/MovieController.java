package com.master.BioskopVozdovac.movie.controller;

import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/movie")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO dto) {
        return new ResponseEntity<>(movieService.saveMovie(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO dto) {
        return new ResponseEntity<>(movieService.updateMovie(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.deleteMovieById(id), HttpStatus.OK);
    }

}
