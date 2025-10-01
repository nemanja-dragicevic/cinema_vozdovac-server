package com.master.BioskopVozdovac.project.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ProjectTimes(@NotNull LocalTime start,
                           @NotNull LocalTime end) {
}
