package com.master.BioskopVozdovac.hall.model;

import com.master.BioskopVozdovac.seat.model.SeatEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotEmpty
    @Column(name = "hall_name")
    private String hallName;

    /**
     * The number of rows in the hall.
     */
    @NotNull
    @Positive
    @Column(name = "rows_count")
    private int rowsCount;

    /**
     * The number of seats per row in the hall.
     */
    @NotNull
    @Positive
    @Column(name = "seats_per_row")
    private int seatsPerRow;

    /**
     * The list of seats in the hall.
     */
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatEntity> seats = new ArrayList<>();

}
