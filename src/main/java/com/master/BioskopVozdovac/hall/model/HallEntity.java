package com.master.BioskopVozdovac.hall.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a hall in the cinema.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hall")
@SuperBuilder(toBuilder = true)
public class HallEntity {

    /**
     * The unique identifier for the hall.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long hallID;

    /**
     * The name of the hall.
     */
    @Column(name = "hall_name")
    private String hallName;

    /**
     * The number of rows in the hall.
     */
    @Column(name = "rows_count")
    private int rowsCount;

    /**
     * The number of seats per row in the hall.
     */
    @Column(name = "seats_per_row")
    private int seatsPerRow;

    /**
     * The list of seats in the hall.
     */
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SeatEntity> seats = new ArrayList<>();

}
