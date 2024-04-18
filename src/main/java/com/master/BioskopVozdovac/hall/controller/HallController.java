package com.master.BioskopVozdovac.hall.controller;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hall")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @GetMapping("/all")
    public ResponseEntity<List<HallDTO>> getAll() {
        return new ResponseEntity<>(hallService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HallDTO> createHall(@RequestBody HallDTO dto) {
        return new ResponseEntity<>(hallService.createHall(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteHall(@PathVariable Long id) {
        return new ResponseEntity<>(hallService.deleteWithId(id), HttpStatus.OK);
    }

}
