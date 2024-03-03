package com.master.BioskopVozdovac.hall.model;

import com.master.BioskopVozdovac.broadcast.model.BroadcastEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hall")
public class HallEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long hallID;

    @NotNull
    private String hallName;

    @OneToMany(mappedBy = "hall")
    private Set<BroadcastEntity> broadcasts = new HashSet<>();

}
