package com.master.BioskopVozdovac.role.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
public record RoleDTO(Long roleID,
                      @NotNull Long actorID,
                      Long movieID,
                      @NotBlank String roleName) {
}
