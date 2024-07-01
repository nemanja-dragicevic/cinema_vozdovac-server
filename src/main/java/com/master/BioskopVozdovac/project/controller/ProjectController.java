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

import java.sql.Date;
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
    public ResponseEntity<String> saveMovieProjections(@RequestBody ProjectionsSave dto) {
        return new ResponseEntity<>(projectService.saveMovieProjections(dto), HttpStatus.OK);
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectTimes>> getAvailableTimes(@RequestBody ProjectionsSave dto) {
        return new ResponseEntity<>(projectService.getAvailableTimes(dto), HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getProjectionsForDate(@PathVariable Date date) {
        return new ResponseEntity<>(projectService.getProjectionsForMovie(date), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ProjectDTO>> getProjectionsForDateAndMovie(@RequestParam("date")  Date date,
                                                                          @RequestParam("id") Long id) {
        return new ResponseEntity<>(projectService.getProjectionsForDateAndMovie(date, id), HttpStatus.OK);
    }

    @GetMapping("/get-projections")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectTimes>> getProjectionsForHall( @RequestParam("hallID") Long hallID,
                                                                   @RequestParam("startDate") Date startDate,
                                                                   @RequestParam("endDate") Date endDate) {
        return new ResponseEntity<>(projectService.getProjectionsForHallAndDates(hallID, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/projections/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ProjectDTO>> getProjectionsForMovie(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectionsForCertainMovie(id), HttpStatus.OK);
    }

    @GetMapping("/{date}/{hallID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectTimes>> getTimesForHallAndDate(@PathVariable Date date, @PathVariable Long hallID) {
        return new ResponseEntity<>(projectService.getProjectionsForDateAndHall(date, hallID), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProjection(@RequestBody ProjectDTO dto) {
        return new ResponseEntity<>(projectService.updateProjection(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteProjectionWithID(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.deleteProjectionWithID(id), HttpStatus.OK);
    }

}
