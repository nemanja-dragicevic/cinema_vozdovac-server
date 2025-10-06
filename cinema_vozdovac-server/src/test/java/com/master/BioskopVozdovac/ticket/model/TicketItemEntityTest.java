package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TicketItemEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    private TicketItemEntity ticketItem;

    @Mock
    private TicketEntity ticketEntity;
    @Mock
    private ProjectEntity projectEntity;
    @Mock
    private SeatEntity seatEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        this.ticketItem = new TicketItemEntity();
        this.ticketItem.setId(1L);
        this.ticketItem.setTicket(ticketEntity);
        this.ticketItem.setProject(projectEntity);
        this.ticketItem.setSeat(seatEntity);
    }

    @Test
    public void testValidTicketItem() {
        Set<ConstraintViolation<TicketItemEntity>> violations = validator.validate(ticketItem);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testTicketNotNull() {
        ticketItem.setTicket(null);

        Set<ConstraintViolation<TicketItemEntity>> violations = validator.validate(ticketItem);

        assertEquals(1, violations.size());
        assertEquals("ticket", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testProjectNotNull() {
        ticketItem.setProject(null);

        Set<ConstraintViolation<TicketItemEntity>> violations = validator.validate(ticketItem);

        assertEquals(1, violations.size());
        assertEquals("project", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testSeatNotNull() {
        ticketItem.setSeat(null);

        Set<ConstraintViolation<TicketItemEntity>> violations = validator.validate(ticketItem);

        assertEquals(1, violations.size());
        assertEquals("seat", violations.iterator().next().getPropertyPath().toString());
    }

}
