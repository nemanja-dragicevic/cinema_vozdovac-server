package com.master.BioskopVozdovac.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum representing roles of members in the system.
 * Implements GrantedAuthority to integrate with Spring Security's role-based authorization.
 *
 * @author Nemanja Dragićević
 */
public enum MemberRole implements GrantedAuthority {

    /**
     * Administrator role.
     */
    ADMIN,

    /**
     * Regular user role.
     */
    USER;

    /**
     * Returns the authority (role name) as a string.
     *
     * @return The name of the enum constant representing the authority (role name).
     */
    @Override
    public String getAuthority() {
        return this.name();
    }
}
