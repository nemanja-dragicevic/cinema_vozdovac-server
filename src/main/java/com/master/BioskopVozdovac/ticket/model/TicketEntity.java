package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Long id;

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity member;

    @NotEmpty
    @Column(name = "payin_time")
    private LocalDateTime payinTime;

    @NotNull
    private int price;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

}
