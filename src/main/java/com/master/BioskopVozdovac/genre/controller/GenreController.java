package com.master.BioskopVozdovac.genre.controller;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/genre")
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenreDTO> saveGenre(@RequestBody GenreDTO dto) {
        return new ResponseEntity<>(genreService.saveGenre(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        return new ResponseEntity<>(genreService.deleteGenre(id), HttpStatus.OK);
    }

}
