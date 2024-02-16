package com.master.BioskopVozdovac.broadcast.controller;

import com.master.BioskopVozdovac.broadcast.model.BroadcastDTO;
import com.master.BioskopVozdovac.broadcast.service.BroadcastService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/screen")
public class BroadcastController {

    private final BroadcastService broadcastService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<BroadcastDTO>> getAllDTOs() {
        return new ResponseEntity<>(broadcastService.getAllDTOs(), HttpStatus.OK);
    }

}
