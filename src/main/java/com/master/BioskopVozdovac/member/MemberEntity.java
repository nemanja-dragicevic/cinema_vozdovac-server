package com.master.BioskopVozdovac.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member")
public class MemberEntity extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long memberID;

    @NotEmpty
    private String firsName;

    @NotEmpty
    private String lastName;

    private String address;

    private String phoneNumber;

    private LocalDate birthDate;


}
