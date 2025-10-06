package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO {

    private Long movieID;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate startTime;

    @NotNull
    private LocalDate endTime;

    private String smallPicture;

    private String bigPicture;

    @NotBlank
    private String description;

    @Positive
    private int duration;

    @NotEmpty
    private Set<GenreDTO> genres;

    private Set<RoleDTO> roleDTO;

}
