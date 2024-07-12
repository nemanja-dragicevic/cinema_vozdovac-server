package com.master.BioskopVozdovac.ticket.service;

import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import com.master.BioskopVozdovac.stripe_config.Constant;
import com.master.BioskopVozdovac.stripe_config.StripeService;
import com.master.BioskopVozdovac.stripe_config.model.CreatePaymentResponse;
import com.master.BioskopVozdovac.stripe_config.model.StripeResponse;
import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketItemDTO;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StripeServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TicketAdapter ticketAdapter;
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private StripeService stripeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(stripeService, "key", "sk_test_4eC39HqLyjWDarjtT1zdp7dc");

    }

    @Test
    public void testCreatePayment() throws StripeException {
        TicketItemDTO itemDTO = new TicketItemDTO();
        itemDTO.setProjectionId(1L);
        itemDTO.setSeatId(1L);

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.getTicketItems().add(itemDTO);
        ticketDTO.setMemberID(1L);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectID(1L);
        projectEntity.setPrice(500L);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName("Test Movie");
        projectEntity.setMovie(movieEntity);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));

        try (MockedStatic<Session> mockedSession = mockStatic(Session.class)) {
            Session session = mock(Session.class);
            when(session.getId()).thenReturn("sess_123");
            when(session.getUrl()).thenReturn("http://example.com");

            mockedSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(session);

            StripeResponse response = stripeService.createPayment(ticketDTO);

            assertEquals(Constant.SUCCESS, response.getStatus());
            assertEquals("Payment session created successfully", response.getMessage());
            assertEquals(200, response.getHttpStatus());
            assertEquals("sess_123", ((CreatePaymentResponse) response.getData()).getSessionId());
            assertEquals("http://example.com", ((CreatePaymentResponse) response.getData()).getSessionUrl());

            verify(projectRepository, times(1)).findById(1L);
        }
    }

//    @Test
//    public void testCapturePaymentSuccess() throws StripeException {
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("memberID", "1");
//        metadata.put("totalSeats", "2");
//        metadata.put("total", "1000");
//        metadata.put("item_1_1", "1,1");
//
//        Session session = mock(Session.class);
//        when(session.getStatus()).thenReturn(Constant.STRIPE_SESSION_STATUS_SUCCESS);
//        when(session.getMetadata()).thenReturn(metadata);
//        when(session.getPaymentIntent()).thenReturn("pi_123");
//
//        mockStatic(Session.class);
//        when(Session.retrieve(anyString())).thenReturn(session);
//
//        TicketEntity ticketEntity = new TicketEntity();
//        when(ticketAdapter.dtoToEntity(any(TicketDTO.class))).thenReturn(ticketEntity);
//
//        TicketDTO ticketDTO = stripeService.capturePayment("sess_123");
//
//        assertEquals(1L, ticketDTO.getMemberID());
//        assertEquals(2, ticketDTO.getTotalSeats());
//        assertEquals(1000L, ticketDTO.getTotal());
//        assertEquals(TicketStatus.PAID, ticketDTO.getStatus());
//        assertEquals(1, ticketDTO.getTicketItems().size());
//        assertEquals(1L, ticketDTO.getTicketItems().iterator().next().getProjectionId());
//        assertEquals(1L, ticketDTO.getTicketItems().iterator().next().getSeatId());
//
//        // Verify interactions
//        verify(ticketRepository, times(1)).save(ticketEntity);
//        Session.retrieve(anyString());
//    }

}
