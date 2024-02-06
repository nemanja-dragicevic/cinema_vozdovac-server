package com.master.BioskopVozdovac.role.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long roleID;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    private ActorEntity actor;

    @ManyToOne
    @NotNull
    @JsonManagedReference
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    private String roleName;

}
