package com.master.BioskopVozdovac.role.controller;

import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO dto) {
        return new ResponseEntity<>(roleService.saveRole(dto), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Set<RoleDTO>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{movieID}")
    public ResponseEntity<Set<RoleDTO>> getRolesForMovieID(@PathVariable Long movieID) {
        return new ResponseEntity<>(roleService.getRolesForMovie(movieID), HttpStatus.OK);
    }

}
