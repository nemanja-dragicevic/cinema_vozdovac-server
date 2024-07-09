package com.master.BioskopVozdovac.ticket.service;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketAdapter ticketAdapter;

    @InjectMocks
    private TicketService ticketService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendForApproval() {
        Long memberID = 1L;
        Long ticketID = 10L;

        MemberEntity member = new MemberEntity();
        member.setMemberID(memberID);

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(ticketID);
        ticketEntity.setMember(member);
        ticketEntity.setPayinTime(LocalDateTime.now());
        ticketEntity.setStatus(TicketStatus.PAID);
        ticketEntity.setTotal(1000L);
        ticketEntity.setTotalSeats(2);
        ticketEntity.setPaymentIntent("abc123");

        TicketDTO expected = new TicketDTO();
        expected.setId(ticketEntity.getId());
        expected.setMemberID(ticketEntity.getMember().getMemberID());
        expected.setPayinTime(ticketEntity.getPayinTime());
        expected.setTotal(ticketEntity.getTotal());
        expected.setStatus(TicketStatus.UNDER_REVIEW);
        expected.setTotalSeats(2);

        when(ticketRepository.findById(ticketID)).thenReturn(Optional.of(ticketEntity));
        when(ticketRepository.saveAndFlush(any(TicketEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        when(ticketAdapter.entityToDTO(ticketEntity)).thenReturn(expected);

        TicketDTO returnedDTO = ticketService.sendForApproval(memberID, ticketID);

        assertEquals(expected.getId(), returnedDTO.getId());
        assertEquals(expected.getMemberID(), returnedDTO.getMemberID());
        assertEquals(expected.getPayinTime(), returnedDTO.getPayinTime());
        assertEquals(expected.getTotal(), returnedDTO.getTotal());
        assertEquals(expected.getStatus(), returnedDTO.getStatus());
        assertEquals(expected.getTotalSeats(), returnedDTO.getTotalSeats());

        verify(ticketRepository, times(1)).findById(ticketID);
        verify(ticketRepository, times(1)).saveAndFlush(ticketEntity);
        verifyNoMoreInteractions(ticketRepository);

        verify(ticketAdapter, times(1)).entityToDTO(ticketEntity);
        verifyNoMoreInteractions(ticketAdapter);
    }

}
