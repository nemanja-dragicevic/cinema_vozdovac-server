package com.master.BioskopVozdovac.actor.model;

import com.master.BioskopVozdovac.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record ActorDTO (Long actorID,
                        @NotBlank String firstName,
                        @NotBlank String lastName,
                        @NotNull Gender gender) {
}
