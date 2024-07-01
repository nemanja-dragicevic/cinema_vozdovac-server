package com.master.BioskopVozdovac.actor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="actor")
@SuperBuilder(toBuilder = true)
public class ActorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long actorID;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<RoleEntity> roles = new HashSet<>();

}
