package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a movie entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
public class MovieEntity {

    /**
     * Unique identifier for the movie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long movieID;

    /**
     * The name of the movie.
     */
    @NotEmpty
    private String name;

    /**
     * The detailed description of the movie.
     */
    @NotEmpty
    private String description;

    /**
     * The duration of the movie in minutes.
     */
    @Positive
    private int duration;

    /**
     * The start time of the movie.
     */
    private LocalDate startTime;

    /**
     * The end time of the movie.
     */
    private LocalDate endTime;

    /**
     * Set of roles associated with this movie.
     * Represents the one-to-many relationship between MovieEntity and RoleEntity.
     */
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<RoleEntity> roles = new HashSet<>();

    /**
     * Set of projects associated with this movie.
     * Represents the one-to-many relationship between MovieEntity and ProjectEntity.
     */
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<ProjectEntity> projects = new HashSet<>();

    /**
     * Set of genres associated with this movie.
     * Represents the many-to-many relationship between MovieEntity and GenreEntity.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres;

}
