package com.master.BioskopVozdovac.actor.service;

import com.master.BioskopVozdovac.actor.adapter.ActorAdapter;
import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles operations related to actors.
 *
 * @author Nemanja Dragićević
 */
@Service
@RequiredArgsConstructor
public class ActorService {

    /**
     * Repository for accessing actor data
     */
    private final ActorRepository actorRepository;

    /**
     * Adapter for converting between DTOs and entities
     */
    private final ActorAdapter actorAdapter;

    /**
     * Saves a new actor based on the provided ActorDTO.
     *
     * @param dto The ActorDTO containing actor details to be saved.
     *
     * @return The saved ActorDTO after converting from the saved ActorEntity.
     */
    public ActorDTO saveActor(ActorDTO dto) {
        ActorEntity entity = actorRepository.saveAndFlush(actorAdapter.dtoToEntity(dto));
        return actorAdapter.entityToDTO(entity);
    }

    /**
     * Retrieves an actor by the specified ID.
     *
     * @param id The ID of the actor to retrieve.
     * @return The ActorDTO representing the actor found by the ID.
     * @throws UserException if no actor is found with the specified ID.
     */
    public ActorDTO getActorById(Long id) {
        ActorEntity entity = actorRepository.findById(id).orElseThrow(()
                -> new UserException("Could not find actor with id: " + id, HttpStatus.NOT_FOUND));
        return actorAdapter.entityToDTO(entity);
    }

    /**
     * Updates an existing actor based on the provided ActorDTO.
     *
     * @param dto The ActorDTO containing updated actor details.
     * @return The updated ActorDTO after converting from the updated ActorEntity.
     */
    public ActorDTO updateActor(ActorDTO dto) {
        ActorEntity entity = actorRepository.saveAndFlush(actorAdapter.dtoToEntity(dto));
        return actorAdapter.entityToDTO(entity);
    }

    /**
     * Deletes an actor by the specified ID.
     *
     * @param id The ID of the actor to delete.
     * @return A success message indicating the deletion of the actor with the specified ID.
     */
    public String deleteActorById(Long id) {
        actorRepository.deleteById(id);
        return "Successfully deleted actor with id: " + id;
    }

    public List<ActorDTO> getAll() {
        return actorAdapter.toDTO(actorRepository.findAll());
    }
}
