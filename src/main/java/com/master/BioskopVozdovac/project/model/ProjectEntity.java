package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Long projectID;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private HallEntity hall;

    @Column(name = "starts_at")
    private LocalDateTime time;

    @Column(name = "ends_at")
    private LocalDateTime projectEnd;

    @Column(name = "price")
    private Long price;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<TicketItemEntity> ticketItems = new HashSet<>();

}
