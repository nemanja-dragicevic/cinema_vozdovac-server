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

    public List<GenreDTO> getAllGenres() {
        return GenreAdapter.toDto(genreRepository.findAll());
    }

    public GenreDTO saveGenre(GenreDTO dto) {
        GenreEntity entity = genreRepository.saveAndFlush(GenreAdapter.dtoToEntity(dto));

        return GenreAdapter.entityToDTO(entity);
    }

    public String deleteGenre(Long id) {
        genreRepository.deleteById(id);
        return "Successfully deleted genre";
    }

    public GenreDTO updateGenre(GenreDTO dto) {
        Optional<GenreEntity> dbEntity = genreRepository.findById(dto.genreID());

        if (dbEntity.isEmpty())
            throw new NoSuchElementException("There is no element with id: " + dto.genreID());

        GenreEntity entity = GenreAdapter.dtoToEntity(dto);

        return GenreAdapter.entityToDTO(genreRepository.saveAndFlush(entity));
    }
}
