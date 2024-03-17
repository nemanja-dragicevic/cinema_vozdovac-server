package com.master.BioskopVozdovac.genre.service;

import com.master.BioskopVozdovac.genre.adapter.GenreAdapter;
import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import com.master.BioskopVozdovac.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreAdapter genreAdapter;

    public List<GenreDTO> getAllGenres() {
        return genreAdapter.toDto(genreRepository.findAll());
    }

    public GenreDTO saveGenre(GenreDTO dto) {
        GenreEntity entity = genreRepository.saveAndFlush(genreAdapter.dtoToEntity(dto));
        dto.setGenreID(entity.getGenreID());

        return dto;
    }

    public String deleteGenre(Long id) {
        genreRepository.deleteById(id);
        return "Successfully deleted genre";
    }
}
