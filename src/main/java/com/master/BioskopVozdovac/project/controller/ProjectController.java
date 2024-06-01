package com.master.BioskopVozdovac.project.controller;

import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.model.ProjectTimes;
import com.master.BioskopVozdovac.project.model.ProjectionsSave;
import com.master.BioskopVozdovac.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/projection")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProjectDTO>> getAllDTOs() {
        return new ResponseEntity<>(projectService.getAllDTOs(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProjectDTO> saveMovieProjections(@RequestBody ProjectionsSave dto) {
        return new ResponseEntity<>(projectService.saveMovieProjections(dto), HttpStatus.OK);
    }

//    @GetMapping("/available")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<List<String>> getAvailableTimes(@RequestBody ProjectionsSave dto) {
//        return new ResponseEntity<>(projectService.getAvailableTimes(dto), HttpStatus.OK);
//    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectTimes>> getAvailableTimes(@RequestBody ProjectionsSave dto) {
        return new ResponseEntity<>(projectService.getAvailableTimes(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getProjectionsForMovie(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectionsForMovie(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> train(@RequestBody List<LocalTime> time) {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
