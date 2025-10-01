package com.master.BioskopVozdovac.seat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a seat entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seat")
@Builder
public class SeatEntity {

    /**
     * Unique identifier for the seat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    private Long id;

    /**
     * The row number of the seat.
     */
    @Column(name = "row_number")
    private int rowNumber;

    /**
     * The seat number within the row.
     */
    @Column(name = "seat_number")
    private int seatNumber;

    /**
     * The hall to which this seat belongs.
     * Represents the many-to-one relationship between SeatEntity and HallEntity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    @JsonBackReference
    private HallEntity hall;

    /**
     * The ticket item associated with this seat.
     * Represents the one-to-one relationship between SeatEntity and TicketItemEntity.
     */
    @OneToOne(mappedBy = "seat")
    private TicketItemEntity ticketItem;

}
