package com.master.BioskopVozdovac.member.controller;

import com.master.BioskopVozdovac.member.model.ChangePassword;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.service.MemberService;
import com.master.BioskopVozdovac.security.MemberAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles HTTP requests related to members.
 *
 * @author Nemanja Dragićević
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members")
public class MemberController {

    /**
     * Private field that holds an instance of the MemberService.
     * This service is responsible for managing member-related operations.
     */
    private final MemberService memberService;

    /**
     * Authentication provider for creating and validating tokens
     */
    private final MemberAuthenticationProvider provider;

    /**
     * Endpoint for registering a new member.
     *
     * @param dto The MemberDTO containing registration details.
     *
     * @return ResponseEntity with MemberDTO and HTTP status OK if registration is successful.
     */
    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto) {
        MemberDTO response = memberService.register(dto);
        response.setToken(provider.createToken(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint for member login.
     *
     * @param dto The MemberDTO containing login credentials.
     *
     * @return ResponseEntity with MemberDTO and HTTP status OK if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<MemberDTO> login(@RequestBody MemberDTO dto) {
        MemberDTO user = memberService.login(dto);
        user.setToken(provider.createToken(user));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.getDtoById(id), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MemberDTO> updateMember(@RequestBody MemberDTO dto) {
        return new ResponseEntity<>(memberService.updateMember(dto), HttpStatus.OK);
    }

    @PutMapping("/change-pass")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<MemberDTO> changePassword(@RequestBody ChangePassword dto) {
        return new ResponseEntity<>(memberService.changePassword(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.deleteMember(id), HttpStatus.OK);
    }

}
