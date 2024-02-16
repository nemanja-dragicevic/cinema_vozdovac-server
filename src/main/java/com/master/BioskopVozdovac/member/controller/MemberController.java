package com.master.BioskopVozdovac.member.controller;

import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import com.master.BioskopVozdovac.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberEntity> saveMember(@RequestBody MemberDTO dto) {
        return new ResponseEntity<>(memberService.saveMember(dto), HttpStatus.OK);
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
