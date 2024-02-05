package com.master.BioskopVozdovac.actor.controller;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/actor")
@AllArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<ActorEntity> saveActor(@RequestBody ActorDTO dto) {
        return new ResponseEntity<>(actorService.saveActor(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.getActorById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ActorDTO> updateActor(@RequestBody ActorDTO dto) {
        return new ResponseEntity<>(actorService.updateActor(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.deleteActorById(id), HttpStatus.OK);
    }

}
