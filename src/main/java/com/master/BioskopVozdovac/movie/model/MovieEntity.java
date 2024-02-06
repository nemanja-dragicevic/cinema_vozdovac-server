package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.role.model.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<RoleEntity> roles = new HashSet<>();

}
