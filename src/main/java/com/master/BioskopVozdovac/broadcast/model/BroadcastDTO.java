package com.master.BioskopVozdovac.broadcast.model;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BroadcastDTO {

    private Long id;
    private MovieDTO movie;
    private HallDTO hall;
    private LocalDateTime broadcastDateTime;

}
