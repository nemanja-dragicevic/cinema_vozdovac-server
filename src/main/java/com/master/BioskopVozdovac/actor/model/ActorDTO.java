package com.master.BioskopVozdovac.actor.model;

import com.master.BioskopVozdovac.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ActorDTO {

    private Long actorID;

    private String firstName;

    private String lastName;

    private Gender gender;

}
