package com.master.BioskopVozdovac.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller class for handling movie-related operations.
 *
 * This controller provides endpoints for managing movies, including saving, retrieving, updating, and deleting movies.
 *
 * @author Nemanja Dragićević
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/movie")
public class MovieController {

    /**
     * Service layer for movie-related operations
     */
    private final MovieService movieService;

    /**
     * Endpoint for saving a new movie.
     *
     * @param movieJSON JSON string representing the MovieDTO.
     * @param file      MultipartFile representing the movie poster image.
     * @return ResponseEntity containing the saved MovieDTO and HttpStatus.OK on success,
     *         or INTERNAL_SERVER_ERROR status on failure.
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('ADMIN')")
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

    /**
     * Endpoint for updating an existing movie.
     *
     * @param movieJSON JSON string representing the updated MovieDTO.
     * @param small     MultipartFile representing the small picture (thumbnail) of the movie.
     * @param big       MultipartFile representing the big picture (poster) of the movie.
     * @return ResponseEntity containing the updated MovieDTO and HttpStatus.OK on success,
     *         or INTERNAL_SERVER_ERROR status on failure.
     */
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieDTO> updateMovie(@RequestParam("movie") String movieJSON,
                                                @RequestParam("smallPicture") MultipartFile small,
                                                @RequestParam("bigPicture") MultipartFile big) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.findAndRegisterModules();
            MovieDTO dto = objectMapper.readValue(movieJSON, MovieDTO.class);
            return new ResponseEntity<>(movieService.updateMovie(dto, small, big), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint for deleting a movie by its ID.
     *
     * @param id The ID of the movie to delete.
     * @return ResponseEntity with a success message and HttpStatus.OK on successful deletion,
     *         or NOT_FOUND status if the movie with the specified ID does not exist.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @GetMapping("/save-projections")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MovieDTO>> getMoviesWithoutProjections() {
        return new ResponseEntity<>(movieService.getMoviesWithoutProjections(), HttpStatus.OK);
    }

    @PostMapping(value = "/try")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO dto) {
        return new ResponseEntity<>(movieService.trySaveMovie(dto), HttpStatus.OK);
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            objectMapper.findAndRegisterModules();
//            MovieDTO dto = objectMapper.readValue(movieJSON, MovieDTO.class);
//        } catch (Exception e) {
//            return new ResponseEntity<>(movieService.trySaveMovie(project.getMovie(), project.getProjections()), HttpStatus.OK);
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
    }

}
