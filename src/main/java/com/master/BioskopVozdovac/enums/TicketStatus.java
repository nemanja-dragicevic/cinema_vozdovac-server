package com.master.BioskopVozdovac.enums;

/**
 * Enum representing the status of a ticket.
 * Provides predefined constants for different states of ticket status.
 *
 * @author Nemanja Dragićević
 */
public enum TicketStatus {

    /**
     * Ticket has not been paid.
     */
    NOT_PAID,

    /**
     * Ticket has been paid.
     */
    PAID,

    /**
     * Ticket is under review.
     */
    UNDER_REVIEW,

    /**
     * Ticket has been revoked.
     */
    REVOKE

}
