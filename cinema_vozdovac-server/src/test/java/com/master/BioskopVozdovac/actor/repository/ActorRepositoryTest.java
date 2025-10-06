package com.master.BioskopVozdovac.actor.repository;

import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.input.ActorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ActorRepositoryTest {

    @Autowired
    private ActorRepository repository;

    private ActorEntity entity;

    @BeforeEach
    void setUp() {
        entity = saveEntity();
    }

    @Test
    void testFindById() {
        ActorEntity foundEntity = repository.findById(entity.getActorID()).orElse(null);

        assertNotNull(foundEntity);
        assertEquals(entity.getActorID(), foundEntity.getActorID());
        assertEquals(entity.getFirstName(), foundEntity.getFirstName());
        assertEquals(entity.getLastName(), foundEntity.getLastName());
    }

    @Test
    void testFindByIdNotFound() {
        repository.deleteById(0L);
        Optional<ActorEntity> actor = repository.findById(0L);
        assertTrue(actor.isEmpty());
    }

    @Test
    void testUpdate() {
        ActorEntity updatedEntity = entity;
        updatedEntity.setFirstName("Jack");
        repository.save(updatedEntity);

        ActorEntity foundEntity = repository.findById(updatedEntity.getActorID()).orElse(null);
        assertNotNull(foundEntity);
        assertEquals(updatedEntity.getActorID(), foundEntity.getActorID());
        assertEquals(updatedEntity.getFirstName(), foundEntity.getFirstName());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(entity.getActorID());
        Optional<ActorEntity> actorEntity = repository.findById(entity.getActorID());
        assertThat(actorEntity).isEmpty();
    }

    ActorEntity saveEntity() {
        return repository.save(ActorData.ACTOR_ENTITY);
    }

}
