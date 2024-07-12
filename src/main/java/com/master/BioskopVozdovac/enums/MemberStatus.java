package com.master.BioskopVozdovac.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the status of a member.
 * Provides predefined constants for active, inactive, and unverified states.
 *
 * @author Nemanja Dragićević
 */
@Getter
@AllArgsConstructor
public enum MemberStatus {

    /**
     * Active member status.
     */
    ACTIVE,

    /**
     * Inactive member status.
     */
    INACTIVE,

    /**
     * Unverified member status.
     */
    UNVERIFIED

}
