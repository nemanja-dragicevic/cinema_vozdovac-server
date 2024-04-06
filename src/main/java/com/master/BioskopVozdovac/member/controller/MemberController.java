package com.master.BioskopVozdovac.member.controller;

import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import com.master.BioskopVozdovac.member.service.MemberService;
import com.master.BioskopVozdovac.security.MemberAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members")
public class MemberController {

    private final MemberService memberService;

    private final MemberAuthenticationProvider provider;

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto) {
        MemberDTO response = memberService.register(dto);
        response.setToken(provider.createToken(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDTO> login(@RequestBody MemberDTO dto) {
        return new ResponseEntity<>(memberService.login(dto), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.getDtoById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MemberEntity> updateMember(@RequestBody MemberDTO dto) {
        return new ResponseEntity<>(memberService.updateMember(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.deleteMember(id), HttpStatus.OK);
    }

}
