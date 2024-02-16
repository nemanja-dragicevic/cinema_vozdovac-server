package com.master.BioskopVozdovac.broadcast.model;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hall")
public class BroadcastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broadcast_id", nullable = false)
    private Long broadcastID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    private HallEntity hall;

    @Column(name = "broadcast_datetime", nullable = false)
    private LocalDateTime broadcastDateTime;

}
