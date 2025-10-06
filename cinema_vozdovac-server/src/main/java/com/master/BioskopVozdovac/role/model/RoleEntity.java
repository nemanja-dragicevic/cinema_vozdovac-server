package com.master.BioskopVozdovac.role.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a role entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class RoleEntity {

    /**
     * Unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long roleID;

    /**
     * The actor associated with this role.
     * Represents the many-to-one relationship between RoleEntity and ActorEntity.
     */
    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    private ActorEntity actor;

    /**
     * The movie associated with this role.
     * Represents the many-to-one relationship between RoleEntity and MovieEntity.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonBackReference
    private MovieEntity movie;

    /**
     * The name of the role actor played in certain movie.
     */
    private String roleName;

    public static RoleEntity create(Long roleID, ActorEntity actor, MovieEntity movie, String roleName) {
        return new RoleEntity(roleID, actor, movie, roleName);
    }

}
