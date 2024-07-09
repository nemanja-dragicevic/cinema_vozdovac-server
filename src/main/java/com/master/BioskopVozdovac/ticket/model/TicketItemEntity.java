package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a ticket item entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_items")
public class TicketItemEntity {

    /**
     * Unique identifier for the ticket item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Long id;

    /**
     * The ticket associated with this ticket item.
     * Represents the many-to-one relationship between TicketItemEntity and TicketEntity.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketEntity ticket;

    /**
     * The projection associated with this ticket item.
     * Represents the many-to-one relationship between TicketItemEntity and ProjectEntity.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    /**
     * The seat associated with this ticket item.
     * Represents the one-to-one relationship between TicketItemEntity and SeatEntity.
     */
    @NotNull
    @OneToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

}
