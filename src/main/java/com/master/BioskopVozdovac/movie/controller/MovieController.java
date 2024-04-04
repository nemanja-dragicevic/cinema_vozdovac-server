package com.master.BioskopVozdovac.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/movie")
public class MovieController {

    private final MovieService movieService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MovieDTO> saveMovie(@RequestParam("movie") String movieJSON, @RequestParam("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.findAndRegisterModules();  // Nece da se parsira LocalDate
            MovieDTO dto = objectMapper.readValue(movieJSON, MovieDTO.class);
            return new ResponseEntity<>(movieService.saveMovie(dto, file), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/allWithAWS")
    public ResponseEntity<List<MovieDTO>> getAllMoviesAWS() {
        return new ResponseEntity<>(movieService.getAllMoviesWithAWS(), HttpStatus.OK);
    }

}
