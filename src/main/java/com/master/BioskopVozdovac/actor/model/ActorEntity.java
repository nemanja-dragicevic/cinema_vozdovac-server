package com.master.BioskopVozdovac.actor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an actor entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="actor")
@SuperBuilder(toBuilder = true)
public class ActorEntity {

    /**
     * Unique identifier for the actor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long actorID;

    /**
     * The first name of the actor.
     */
    private String firstName;

    /**
     * The last name of the actor.
     */
    private String lastName;

    /**
     * The gender of the actor.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Roles played by this actor.
     * Represents the relationship between ActorEntity and RoleEntity.
     */
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<RoleEntity> roles = new HashSet<>();

    private ActorEntity(Long id, String firstName, String lastName, Gender gender) {
        this.actorID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public static ActorEntity create(Long id, String firstName, String lastName, Gender gender) {
        return new ActorEntity(id, firstName, lastName, gender);
    }

}
