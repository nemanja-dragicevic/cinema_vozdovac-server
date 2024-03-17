package com.master.BioskopVozdovac.project.controller;

import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/screen")
public class ProjectController {

    private final ProjectService broadcastService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProjectDTO>> getAllDTOs() {
        return new ResponseEntity<>(broadcastService.getAllDTOs(), HttpStatus.OK);
    }

}
