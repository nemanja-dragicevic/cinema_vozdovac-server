package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a project entity in the application.
 *
 * @author Nemanja Dragićević
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
@Builder
public class ProjectEntity {

    /**
     * Unique identifier for the projection.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Long projectID;

    /**
     * The movie associated with this projection.
     * Represents the many-to-one relationship between ProjectEntity and MovieEntity.
     */
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    /**
     * The hall associated with this project.
     * Represents the many-to-one relationship between ProjectEntity and HallEntity.
     */
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private HallEntity hall;

    /**
     * The start time of the projection.
     */
    @Column(name = "starts_at")
    private LocalDateTime time;

    /**
     * The end time of the projection.
     */
    @Column(name = "ends_at")
    private LocalDateTime projectEnd;

    /**
     * The price of the projection.
     */
    @Column(name = "price")
    private Long price;

    /**
     * Set of ticket items associated with this project.
     * Represents the one-to-many relationship between ProjectEntity and TicketItemEntity.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<TicketItemEntity> ticketItems = new HashSet<>();

}
