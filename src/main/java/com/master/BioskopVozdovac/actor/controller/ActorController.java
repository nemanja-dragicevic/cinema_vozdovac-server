package com.master.BioskopVozdovac.actor.controller;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle HTTP requests related to actors.
 *
 * @author Nemanja Dragićević
 */
@RestController
@RequestMapping(value = "/api/actor")
@RequiredArgsConstructor
public class ActorController {

    /**
     * Represents service class for entity ActorEntity.
     */
    private final ActorService actorService;

    /**
     * Retrieves a list of all actors.
     *
     * @return ResponseEntity containing a list of ActorDTO objects and HTTP status code OK (200).
     */
    @GetMapping("/all")
    public ResponseEntity<List<ActorDTO>> getAll() {
        return new ResponseEntity<>(actorService.getAll(), HttpStatus.OK);
    }

    /**
     * Saves a new actor.
     *
     * @param dto The ActorDTO object representing the actor to be saved.
     *
     * @return ResponseEntity containing the saved ActorDTO object and HTTP status code OK (200).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ActorDTO> saveActor(@Valid @RequestBody ActorDTO dto) {
        return new ResponseEntity<>(actorService.saveActor(dto), HttpStatus.OK);
    }

    /**
     * Retrieves an actor by ID.
     *
     * @param id The ID of the actor to be retrieved.
     *
     * @return ResponseEntity containing the ActorDTO object with the specified ID and HTTP status code OK (200).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.getActorById(id), HttpStatus.OK);
    }


    /**
     * Updates an existing actor.
     *
     * @param dto The ActorDTO object representing the updated actor.
     *
     * @return ResponseEntity containing the updated ActorDTO object and HTTP status code OK (200).
     */
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ActorDTO> updateActor(@RequestBody ActorDTO dto) {
        return new ResponseEntity<>(actorService.updateActor(dto), HttpStatus.OK);
    }

    /**
     * Deletes an actor by ID.
     *
     * @param id The ID of the actor to be deleted.
     *
     * @return ResponseEntity containing a success message and HTTP status code OK (200).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.deleteActorById(id), HttpStatus.OK);
    }
}
