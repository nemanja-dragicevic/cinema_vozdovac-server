package com.master.BioskopVozdovac.ticket.service;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import com.master.BioskopVozdovac.project.adapter.ProjectAdapter;
import com.master.BioskopVozdovac.seat.adapter.SeatAdapter;
import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketAdapter ticketAdapter;
    @Mock
    private ProjectAdapter projectAdapter;
    @Mock
    private SeatAdapter seatAdapter;

    @InjectMocks
    private TicketService ticketService;

    private TicketEntity ticket;
    private TicketDTO ticketDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new TicketEntity();
        ticket.setId(1L);
        ticket.setPayinTime(LocalDateTime.now());
        ticket.setTotal(100L);
        ticket.setStatus(TicketStatus.PAID);
        ticket.setTotalSeats(2);
        ticket.setTicketItems(new HashSet<>());

        ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setPayinTime(LocalDateTime.now());
        ticketDTO.setTotal(100L);
        ticketDTO.setStatus(TicketStatus.PAID);
        ticketDTO.setTotalSeats(2);
        ticketDTO.setTicketItems(new HashSet<>());
    }

    @Test
    void testGetTicketsUser() {
        when(ticketRepository.findByMemberMemberID(anyLong())).thenReturn(Collections.singletonList(ticket));
        when(ticketAdapter.toDTOs(anyList())).thenReturn(Collections.singletonList(ticketDTO));

        List<TicketDTO> result = ticketService.getTicketsUser(1L);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(ticketRepository, times(1)).findByMemberMemberID(1L);
    }

    // TODO ...
    @Test
    void testGetTicketItems() {
//        TicketItemEntity ticketItemEntity = new TicketItemEntity();
//        ticketItemEntity.setProject(new ProjectEntity());
//        ticketItemEntity.setSeat(new SeatEntity());
//        ticket.setTicketItems(Set.of(ticketItemEntity));
//
//        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
//        when(projectAdapter.entityToDTO(any(ProjectEntity.class))).thenReturn(new ProjectDTO());
//        when(seatAdapter.entityToDTO(any(SeatEntity.class))).thenReturn(new SeatDTO());
//
//        List<TicketItems> result = ticketService.getTicketItems(1L);
//
//        assertThat(result).isNotEmpty();
//        verify(ticketRepository, times(1)).findById(1L);
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

    @Test
    public void testSendForApprovalInvalidMember() {
        MemberEntity member = new MemberEntity();
        member.setMemberID(2L);
        ticket.setMember(member);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ticketService.sendForApproval(1L, 1L));

        assertEquals("Please select your ticket", exception.getMessage());
        verify(ticketRepository, times(0)).saveAndFlush(ticket);
    }

    @Test
    public void testSendForApprovalNoSuchTicket() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> ticketService.sendForApproval(1L, 1L));

        assertEquals("There is no such ticket", exception.getMessage());
        verify(ticketRepository, times(0)).saveAndFlush(ticket);
    }

    @Test
    void testGetRefundRequests() {
        ticket.setStatus(TicketStatus.UNDER_REVIEW);
        ticketDTO.setStatus(TicketStatus.UNDER_REVIEW);
        when(ticketRepository.findByStatus(TicketStatus.UNDER_REVIEW)).thenReturn(Collections.singletonList(ticket));
        when(ticketAdapter.entityToDTO(any(TicketEntity.class))).thenReturn(ticketDTO);

        List<TicketDTO> result = ticketService.getRefundRequests();

        assertThat(result).isNotEmpty();
        verify(ticketRepository, times(1)).findByStatus(TicketStatus.UNDER_REVIEW);
    }

    // TODO ...
    @Test
    void testGetBookedSeatsForProjection() {
//        TicketItemEntity ticketItemEntity = new TicketItemEntity();
//        ticketItemEntity.setProject(new ProjectEntity());
//        ticketItemEntity.setSeat(new SeatEntity());
//
//        when(ticketRepository.findByStatusAndProjectID(TicketStatus.PAID, 1L)).thenReturn(Collections.singletonList(ticketItemEntity));
//        when(projectAdapter.entityToDTO(any(ProjectEntity.class))).thenReturn(new ProjectDTO());
//        when(seatAdapter.entityToDTO(any(SeatEntity.class))).thenReturn(new SeatDTO());
//
//        List<TicketItems> result = ticketService.getBookedSeatsForProjection(1L);
//
//        assertThat(result).isNotEmpty();
//        verify(ticketRepository, times(1)).findByStatusAndProjectID(TicketStatus.PAID, 1L);
    }

}
