package com.master.BioskopVozdovac.user;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Base abstract class representing a user in the system.
 * This class serves as a blueprint for user entities with common properties like username, password, and email.
 *
 * @author Nemanja Dragićević
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder(toBuilder = true)
public abstract class User {

    /**
     * The username of the user.
     */
    protected String username;


    /**
     * The password of the user.
     */
    protected String password;


    /**
     * The email of the user.
     */
    protected String email;

}
