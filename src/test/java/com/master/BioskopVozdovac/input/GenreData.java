package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;

public class GenreData {

    public static final GenreDTO GENRE_DTO = new GenreDTO(1L, "Action");

    public static final GenreEntity GENRE_ENTITY = GenreEntity.create(1L, "Action");

}
