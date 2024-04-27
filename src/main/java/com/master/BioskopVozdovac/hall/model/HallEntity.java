package com.master.BioskopVozdovac.hall.model;

import com.master.BioskopVozdovac.seat.model.SeatEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "hall_name")
    private String hallName;

    @NotNull
    @Column(name = "capacity")
    private int hallCapacity;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<SeatEntity> seats = new ArrayList<>();

//    @OneToMany(mappedBy = "hall")
//    private Set<ProjectEntity> broadcasts;

}
