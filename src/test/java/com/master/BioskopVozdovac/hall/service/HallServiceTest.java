package com.master.BioskopVozdovac.hall.service;

import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import com.master.BioskopVozdovac.seat.adapter.SeatAdapter;
import com.master.BioskopVozdovac.seat.model.SeatDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.master.BioskopVozdovac.input.HallData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HallServiceTest {

    @Mock
    private HallRepository hallRepository;

    @Mock
    private HallAdapter hallAdapter;

    @Mock
    private SeatAdapter seatAdapter;

    @InjectMocks
    private HallService hallService;

    private HallEntity hallEntity;
    private HallDTO hallDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        hallEntity = HALL_ENTITY;
        hallDTO = HALL_DTO;
    }

    private List<SeatDTO> createDummySeatDTOs() {
        List<SeatDTO> seatDTOs = new ArrayList<>();

        // Create dummy SeatDTOs
        for (int i = 1; i <= 5; i++) { // Assuming 5 rows and 5 seats per row
            for (int j = 1; j <= 5; j++) {
                SeatDTO seatDTO = new SeatDTO();
                seatDTO.setRowNumber(i);
                seatDTO.setSeatNumber(j);
                // Set other properties as needed

                seatDTOs.add(seatDTO);
            }
        }

        return seatDTOs;
    }

    @Test
    void testCreateHall() {
        when(hallAdapter.dtoToEntity(any(HallDTO.class))).thenReturn(hallEntity);
        when(hallRepository.save(any(HallEntity.class))).thenReturn(hallEntity);
        when(hallAdapter.entityToDTO(any(HallEntity.class))).thenReturn(hallDTO);

        List<SeatDTO> seatDTOS = createDummySeatDTOs();
        hallDTO.setSeats(seatDTOS);

        HallDTO created = hallService.createHall(hallDTO);

        assertNotNull(created);
        assertEquals(1L, created.getHallID());
        assertEquals("Sala Uno", created.getHallName());
        assertEquals(5, created.getRowsCount());
        assertEquals(5, created.getSeatsPerRow());
        assertEquals(25, created.getSeats().size());
    }

    @Test
    void testDeleteWithId() {
        Long hallId = 1L;

        String result = hallService.deleteWithId(hallId);

        assertEquals("Successfully deleted hall", result);
        verify(hallRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetAll() {
        when(hallRepository.findAll()).thenReturn(Collections.singletonList(hallEntity));
        when(hallAdapter.toDTO(Collections.singletonList(hallEntity))).thenReturn(Collections.singletonList(hallDTO));

        List<HallDTO> hallDTOS = hallService.getAll();

        assertNotNull(hallDTOS);
        assertFalse(hallDTOS.isEmpty());
        assertEquals(1, hallDTOS.size());
        assertEquals(hallDTO.getHallID(), hallDTOS.get(0).getHallID());
        assertEquals(hallDTO.getHallName(), hallDTOS.get(0).getHallName());
    }

    @Test
    void testUpdateHall() {
        when(hallAdapter.dtoToEntity(any(HallDTO.class))).thenReturn(hallEntity);
        when(hallRepository.saveAndFlush(any(HallEntity.class))).thenReturn(hallEntity);
        when(hallAdapter.entityToDTO(any(HallEntity.class))).thenReturn(hallDTO);

        HallDTO updated = hallService.updateHall(hallDTO);

        assertNotNull(updated);
        assertEquals(hallDTO.getHallID(), updated.getHallID());
        assertEquals(hallDTO.getHallName(), updated.getHallName());
        assertEquals(hallDTO.getRowsCount(), updated.getRowsCount());
        assertEquals(hallDTO.getSeatsPerRow(), updated.getSeatsPerRow());
    }

}
