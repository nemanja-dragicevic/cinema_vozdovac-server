package com.master.BioskopVozdovac.actor.controller;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/actor")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping("/all")
    public ResponseEntity<List<ActorDTO>> getAll() {
        return new ResponseEntity<>(actorService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ActorEntity> saveActor(@RequestBody ActorDTO dto) {
        return new ResponseEntity<>(actorService.saveActor(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.getActorById(id), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ActorDTO> updateActor(@RequestBody ActorDTO dto) {
        return new ResponseEntity<>(actorService.updateActor(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.deleteActorById(id), HttpStatus.OK);
    }
}
