package com.master.BioskopVozdovac.genre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Represents a genre entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "genre")
public class GenreEntity {

    /**
     * Unique identifier for the genre.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", nullable = false)
    private Long genreID;

    /**
     * The name of the genre.
     */
    @NotEmpty
    private String name;

    /**
     * Set of movies associated with this genre.
     * Represents the many-to-many relationship between GenreEntity and MovieEntity.
     */
    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private Set<MovieEntity> movies;

}
