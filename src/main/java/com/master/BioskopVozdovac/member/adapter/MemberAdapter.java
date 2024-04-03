package com.master.BioskopVozdovac.member.adapter;

import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberAdapter {

    public MemberEntity dtoToEntity(MemberDTO dto) {
        if(dto == null)
            return null;

        final MemberEntity entity =new MemberEntity();

        entity.setMemberID(dto.getMemberID());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setStatus(dto.getStatus());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    public MemberDTO entityToDTO(MemberEntity entity) {
        if (entity == null)
            return null;
        final MemberDTO dto = new MemberDTO();

        dto.setMemberID(entity.getMemberID());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setStatus(entity.getStatus());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());

        return dto;
    }

}
