package com.master.BioskopVozdovac.genre.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.persistence.*;
import lombok.*;
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
    private String name;

    /**
     * Set of movies associated with this genre.
     * Represents the many-to-many relationship between GenreEntity and MovieEntity.
     */
    @ManyToMany(mappedBy = "genres")
    @JsonBackReference
    private Set<MovieEntity> movies;

    private GenreEntity(Long genreID, String name) {
        this.genreID = genreID;
        this.name = name;
    }

    public static GenreEntity create(Long genreID, String name) {
        return new GenreEntity(genreID, name);
    }

}
