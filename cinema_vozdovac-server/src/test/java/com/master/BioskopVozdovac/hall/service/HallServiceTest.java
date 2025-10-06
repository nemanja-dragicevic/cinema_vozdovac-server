package com.master.BioskopVozdovac.hall.service;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static com.master.BioskopVozdovac.input.HallData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HallServiceTest {

    @InjectMocks
    private HallService hallService;

    @Mock
    private HallRepository hallRepository;

    private HallEntity hallEntity;
    private HallDTO hallDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hallEntity = HALL_ENTITY;
        hallDTO = HALL_DTO;
    }

    @Test
    void testCreateHall() {
        when(hallRepository.save(any(HallEntity.class))).thenReturn(hallEntity);

        HallDTO created = hallService.createHall(CREATE_HALL);

        assertNotNull(created);
        assertEquals("Sala Uno", created.hallName());
        assertEquals(5, created.rowsCount());
        assertEquals(5, created.seatsPerRow());
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

        List<HallDTO> hallDTOS = hallService.getAll();

        assertNotNull(hallDTOS);
        assertFalse(hallDTOS.isEmpty());
        assertEquals(1, hallDTOS.size());
        assertEquals(hallDTO.hallName(), hallDTOS.get(0).hallName());
    }

    @Test
    void testUpdateHall() {
        when(hallRepository.saveAndFlush(any(HallEntity.class))).thenReturn(hallEntity);

        HallDTO updated = hallService.updateHall(hallDTO);

        assertNotNull(updated);
        assertEquals(hallDTO.hallName(), updated.hallName());
        assertEquals(hallDTO.rowsCount(), updated.rowsCount());
        assertEquals(hallDTO.seatsPerRow(), updated.seatsPerRow());
    }

}
