package com.master.BioskopVozdovac.genre.service;

import com.master.BioskopVozdovac.genre.adapter.GenreAdapter;
import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import com.master.BioskopVozdovac.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public GenreDTO updateGenre(GenreDTO dto) {
        Optional<GenreEntity> dbEntity = genreRepository.findById(dto.getGenreID());

        if (dbEntity.isEmpty())
            throw new NoSuchElementException("There is no element with id: " + dto.getGenreID());

        GenreEntity entity = genreAdapter.dtoToEntity(dto);

        return genreAdapter.entityToDTO(genreRepository.saveAndFlush(entity));
    }
}
