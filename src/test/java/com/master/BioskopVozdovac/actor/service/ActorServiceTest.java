package com.master.BioskopVozdovac.actor.service;

import com.master.BioskopVozdovac.actor.adapter.ActorAdapter;
import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.exception.UserException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(actorAdapter.entityToDTO(ACTOR_ENTITY))
                .thenReturn(ACTOR_DTO);

        when(actorAdapter.dtoToEntity(ACTOR_DTO))
                .thenReturn(ACTOR_ENTITY);
    }

    @Test
    void testSaveActor() {
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
    void testGetActorById() {
        ActorEntity actorEntity = ACTOR_ENTITY;

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setActorID(0L);
        actorDTO.setFirstName("Jack");
        actorDTO.setLastName("Sparrow");
        actorDTO.setGender(Gender.MALE);

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actorEntity));
        when(actorAdapter.entityToDTO(any(ActorEntity.class))).thenReturn(actorDTO);

        ActorDTO found = actorService.getActorById(0L);

        assertNotNull(found);
        assertEquals("Jack", found.getFirstName());
        verify(actorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetActorByIdNotFound() {
        when(actorRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            actorService.getActorById(1L);
        });

        assertEquals("Could not find actor with id: 1", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(actorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateActor() {
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

    @Test
    void testGetAll() {
        List<ActorEntity> entites = new ArrayList<>();
        entites.add(ACTOR_ENTITY);
        UPDATED_ACTOR_ENTITY.setActorID(2L);
        UPDATED_ACTOR_ENTITY.setGender(Gender.MALE);
        entites.add(UPDATED_ACTOR_ENTITY);

        List<ActorDTO> dtos = new ArrayList<>();
        dtos.add(ACTOR_DTO);
        UPDATED_ACTOR_DTO.setActorID(2L);
        UPDATED_ACTOR_DTO.setGender(Gender.MALE);
        dtos.add(UPDATED_ACTOR_DTO);

        when(actorRepository.findAll()).thenReturn(entites);
        when(actorAdapter.toDTO(anyList())).thenReturn(dtos);

        List<ActorDTO> all = actorService.getAll();

        assertNotNull(all);
        assertEquals(2, all.size());

        verify(actorRepository, times(1)).findAll();
        verify(actorAdapter, times(1)).toDTO(anyList());

        assertEquals(0L, all.get(0).getActorID());
        assertEquals("Jack", all.get(0).getFirstName());
        assertEquals("Sparrow", all.get(0).getLastName());

        assertEquals(2L, all.get(1).getActorID());
        assertEquals("Jackie", all.get(1).getFirstName());
        assertEquals("Chan", all.get(1).getLastName());
    }

}