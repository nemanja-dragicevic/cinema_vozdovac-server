package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.user.User;
import com.master.BioskopVozdovac.util.PatternUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a member entity in the application, extending the User class.
 *
 * @author Nemanja Dragićević
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="members")
@SuperBuilder(toBuilder = true)
public class MemberEntity extends User {

    /**
     * Unique identifier for the member.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long memberID;

    /**
     * The first name of the member.
     */
    @NotEmpty
    private String firstName;

    /**
     * The last name of the member.
     */
    @NotEmpty
    private String lastName;

    /**
     * The birth date of the member.
     */
    private LocalDate birthDate;

    /**
     * The role of the member.
     */
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    /**
     * The gender of the member.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * The email address of the member.
     */
    private String email;

    /**
     * The username of the member.
     */
    @NotEmpty
    @Pattern(regexp = PatternUtils.USERNAME_PATTERN)
    private String username;

    /**
     * The password of the member.
     */
    @NotEmpty
    @Pattern(regexp = PatternUtils.PASSWORD_PATTERN)
    private String password;

    /**
     * Set of tickets associated with this member.
     * Represents the one-to-many relationship between MemberEntity and TicketEntity.
     */
    @OneToMany(mappedBy = "member")
    private Set<TicketEntity> tickets = new HashSet<>();

}
