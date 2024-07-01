package com.master.BioskopVozdovac.service;

import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.master.BioskopVozdovac.input.HallData.*;
import static org.mockito.Mockito.when;

public class HallService {

    @Mock
    private HallRepository hallRepository;

    @Mock
    private HallAdapter hallAdapter;

    @InjectMocks
    private HallService hallService;

    @PersistenceContext
    EntityManager entityManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(hallAdapter.entityToDTO(HALL_ENTITY))
                .thenReturn(HALL_DTO);

        when(hallAdapter.dtoToEntity(HALL_DTO))
                .thenReturn(HALL_ENTITY);
    }

}
