package com.master.BioskopVozdovac.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTimes {

    private LocalTime start;
    private LocalTime end;

    public ProjectTimes(String startTime, String endTime) {
        this.start = LocalTime.parse(startTime);
        this.end = LocalTime.parse(endTime);
    }

}
