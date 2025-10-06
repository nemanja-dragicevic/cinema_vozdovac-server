package com.master.BioskopVozdovac.actor.service;

import com.master.BioskopVozdovac.actor.adapter.ActorAdapter;
import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class that handles operations related to actors.
 *
 * @author Nemanja Dragićević
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {

    /**
     * Repository for accessing actor data
     */
    private final ActorRepository actorRepository;

    /**
     * Saves a new actor based on the provided ActorDTO.
     *
     * @param dto The ActorDTO containing actor details to be saved.
     *
     * @return The saved ActorDTO after converting from the saved ActorEntity.
     */
    @Transactional
    public ActorDTO saveActor(ActorDTO dto) {
        ActorEntity entity = actorRepository.save(ActorAdapter.dtoToEntity(dto));
        return ActorAdapter.entityToDTO(entity);
    }

    /**
     * Retrieves an actor by the specified ID.
     *
     * @param id The ID of the actor to retrieve.
     * @return The ActorDTO representing the actor found by the ID.
     * @throws NotFoundException if no actor is found with the specified ID.
     */
    public ActorDTO getActorById(Long id) {
        ActorEntity entity = actorRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Could not find actor with id: " + id));
        return ActorAdapter.entityToDTO(entity);
    }

    /**
     * Updates an existing actor based on the provided ActorDTO.
     *
     * @param dto The ActorDTO containing updated actor details.
     * @return The updated ActorDTO after converting from the updated ActorEntity.
     */
    @Transactional
    public ActorDTO updateActor(ActorDTO dto) {
        ActorEntity entity = actorRepository.findById(dto.actorID())
                .orElseThrow(() -> new NotFoundException("Actor not found"));

        updateActorEntity(entity, dto);

        ActorEntity savedEntity = actorRepository.save(entity);
        return ActorAdapter.entityToDTO(savedEntity);
    }

    private void updateActorEntity(ActorEntity entity, ActorDTO dto) {
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setGender(dto.gender());
    }

    /**
     * Deletes an actor by the specified ID.
     *
     * @param id The ID of the actor to delete.
     * @return A success message indicating the deletion of the actor with the specified ID.
     */
    @Transactional
    public String deleteActorById(Long id) {
        if (!actorRepository.existsById(id))
            throw new NotFoundException("Actor not found");

        actorRepository.deleteById(id);
        return "Successfully deleted actor with id: " + id;
    }

    /**
     * Retrieves a list of all actors from the database and converts them to DTOs (Data Transfer Objects).
     * Uses the ActorAdapter to convert entities fetched from the ActorRepository into ActorDTOs.
     *
     * @return List of ActorDTO objects representing all actors in the database.
     */
    public List<ActorDTO> getAll() {
        return ActorAdapter.toDTO(actorRepository.findAll());
    }

    public List<ActorDTO> getActorsByIds(List<Long> ids) {
        return ActorAdapter.toDTO(actorRepository.findAllById(ids));
    }

}
