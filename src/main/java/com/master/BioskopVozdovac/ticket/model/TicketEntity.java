package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a ticket entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity {

    /**
     * Unique identifier for the ticket.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Long id;

    /**
     * The member who purchased the ticket.
     * Represents the many-to-one relationship between TicketEntity and MemberEntity.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity member;

    /**
     * The date and time when the payment was made for the ticket.
     */
    @NotNull
    @Column(name = "payin_time")
    private LocalDateTime payinTime;

    /**
     * The total amount paid for the ticket.
     */
    @NotNull
    @Positive
    private Long total;

    /**
     * The status of the ticket.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    /**
     * The total number of seats booked in the ticket.
     */
    @Positive
    @Column(name = "total_seats")
    private int totalSeats;

    /**
     * Set of ticket items associated with this ticket.
     * Represents the one-to-many relationship between TicketEntity and TicketItemEntity.
     */
    @NotEmpty
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Set<TicketItemEntity> ticketItems = new HashSet<>();

    /**
     * The ID of the payment intent associated with this ticket.
     */
    @NotNull
    @Column(name = "pi_id")
    private String paymentIntent;

}
