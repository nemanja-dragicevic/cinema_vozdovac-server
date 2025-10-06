package com.master.BioskopVozdovac.genre.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record GenreDTO(Long genreID,
                @NotBlank String name) {}
