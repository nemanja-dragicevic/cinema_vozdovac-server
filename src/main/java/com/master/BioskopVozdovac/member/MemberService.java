package com.master.BioskopVozdovac.member;

import com.master.BioskopVozdovac.exception.UserException;
import com.master.BioskopVozdovac.member.adapter.MemberAdapter;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberAdapter memberAdapter;

    private final MemberRepository memberRepository;

    public MemberEntity saveMember(MemberDTO dto) {
        return memberRepository.saveAndFlush(memberAdapter.dtoToEntity(dto));
    }

    public MemberDTO getDtoById(Long id) {
        MemberEntity entity = memberRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("There is no member with id: " + id));
        return memberAdapter.entityToDTO(entity);
    }

    public MemberEntity updateMember(@RequestBody MemberDTO dto) {
        MemberEntity entity = memberRepository.findById(dto.getMemberID()).orElseThrow(()
                    -> new UserException("Member with id: " + dto.getMemberID() + " doesn't exist",
                    HttpStatus.NOT_FOUND));
        MemberEntity updated = memberAdapter.dtoToEntity(dto);
        updated.setUsername(entity.getUsername());
        updated.setPassword(entity.getPassword());
        updated.setEmail(entity.getEmail());
        return memberRepository.saveAndFlush(updated);
    }

    public String deleteMember(Long id) {
        memberRepository.deleteById(id);
        return "Successfully deleted member with id: " + id;
    }
}
