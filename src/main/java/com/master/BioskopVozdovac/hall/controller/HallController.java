package com.master.BioskopVozdovac.hall.controller;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling HTTP requests related to halls.
 *
 * This controller provides endpoints for retrieving, creating, updating, and deleting halls and their related seats.
 *
 * @author Nemanja Dragićević
 */
@RestController
@RequestMapping(value = "/api/hall")
@RequiredArgsConstructor
public class HallController {

    /**
     * Service layer for handling hall-related operations
     */
    private final HallService hallService;

    /**
     * Retrieves all halls.
     *
     * @return A ResponseEntity containing a list of HallDTOs representing all halls found.
     */
    @GetMapping
    public ResponseEntity<List<HallDTO>> getAll() {
        return new ResponseEntity<>(hallService.getAll(), HttpStatus.OK);
    }

    /**
     * Creates a new hall.
     *
     * @param dto The HallDTO containing hall details to be created.
     * @return A ResponseEntity containing the created HallDTO.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HallDTO> createHall(@RequestBody HallDTO dto) {
        return new ResponseEntity<>(hallService.createHall(dto), HttpStatus.OK);
    }

    /**
     * Updates an existing hall.
     *
     * @param dto The HallDTO containing updated hall details.
     * @return A ResponseEntity containing the updated HallDTO.
     */
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HallDTO> updateHall(@RequestBody HallDTO dto) {
        return new ResponseEntity<>(hallService.updateHall(dto), HttpStatus.OK);
    }

    /**
     * Deletes a hall by the specified ID.
     *
     * @param id The ID of the hall to delete.
     * @return A ResponseEntity containing a success message indicating the deletion of the hall.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteHall(@PathVariable Long id) {
        return new ResponseEntity<>(hallService.deleteWithId(id), HttpStatus.OK);
    }

}
