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

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    private final ActorAdapter actorAdapter;

    public ActorDTO saveActor(ActorDTO dto) {
        ActorEntity entity = actorRepository.saveAndFlush(actorAdapter.dtoToEntity(dto));
        return actorAdapter.entityToDTO(entity);
    }

    public ActorDTO getActorById(Long id) {
        ActorEntity entity = actorRepository.findById(id).orElseThrow(()
                -> new UserException("Could not find actor with id: " + id, HttpStatus.NOT_FOUND));
        return actorAdapter.entityToDTO(entity);
    }

    public ActorDTO updateActor(ActorDTO dto) {
        ActorEntity entity = actorRepository.saveAndFlush(actorAdapter.dtoToEntity(dto));
        return actorAdapter.entityToDTO(entity);
    }

    public String deleteActorById(Long id) {
        actorRepository.deleteById(id);
        return "Successfully deleted actor with id: " + id;
    }

    public List<ActorDTO> getAll() {
        return actorAdapter.toDTO(actorRepository.findAll());
    }
}
