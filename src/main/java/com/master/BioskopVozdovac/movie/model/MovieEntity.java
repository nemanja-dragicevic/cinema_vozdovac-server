package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.broadcast.model.BroadcastEntity;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long movieID;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String duration;

    @NotNull
    private LocalDate startTime;

    private LocalDate endTime;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<BroadcastEntity> broadcasts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres;

}
