package com.master.BioskopVozdovac.service;

import com.master.BioskopVozdovac.actor.adapter.ActorAdapter;
import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.actor.service.ActorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.master.BioskopVozdovac.input.ActorData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorAdapter actorAdapter;

    @InjectMocks
    private ActorService actorService;

    @PersistenceContext
    EntityManager entityManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(actorAdapter.entityToDTO(ACTOR_ENTITY))
                .thenReturn(ACTOR_DTO);

        when(actorAdapter.dtoToEntity(ACTOR_DTO))
                .thenReturn(ACTOR_ENTITY);
    }

    @Test
    public void testSaveActor() {
        when(actorRepository.saveAndFlush(any(ActorEntity.class)))
                .thenReturn(ACTOR_ENTITY);

        ActorDTO resultDto = actorService.saveActor(ACTOR_DTO);

        verify(actorAdapter, times(1)).dtoToEntity(ACTOR_DTO);
        verify(actorRepository, times(1)).saveAndFlush(any(ActorEntity.class));
        verify(actorAdapter, times(1)).entityToDTO(ACTOR_ENTITY);

        assertEquals(ACTOR_ENTITY.getActorID(), resultDto.getActorID());
        assertEquals(ACTOR_ENTITY.getFirstName(), resultDto.getFirstName());
        assertEquals(ACTOR_ENTITY.getLastName(), resultDto.getLastName());
        assertEquals(ACTOR_ENTITY.getGender(), resultDto.getGender());
    }

    @Test
    public void testUpdateActor() {
        when(actorRepository.saveAndFlush(ACTOR_ENTITY)).thenReturn(ACTOR_ENTITY);
        when(actorRepository.findById(ACTOR_ENTITY.getActorID())).thenReturn(Optional.of(ACTOR_ENTITY));
        when(actorRepository.saveAndFlush(UPDATED_ACTOR_ENTITY)).thenReturn(UPDATED_ACTOR_ENTITY);
        when(actorAdapter.entityToDTO(UPDATED_ACTOR_ENTITY)).thenReturn(UPDATED_ACTOR_DTO);
        when(actorAdapter.dtoToEntity(UPDATED_ACTOR_DTO)).thenReturn(UPDATED_ACTOR_ENTITY);

        ActorDTO created = actorService.saveActor(ACTOR_DTO);

        ActorDTO updated = actorService.updateActor(UPDATED_ACTOR_DTO);

        assertNotNull(updated);

        assertNotEquals(updated.getFirstName(), created.getFirstName());
        assertNotEquals(updated.getLastName(), created.getLastName());
    }

    @Test
    public void testDeleteActor() {
        Long actorID = 1L;

        String message = actorService.deleteActorById(actorID);

        verify(actorRepository, times(1)).deleteById(actorID);
        assertEquals("Successfully deleted actor with id: " + actorID, message);
    }

}