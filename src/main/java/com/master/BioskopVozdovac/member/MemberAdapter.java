package com.master.BioskopVozdovac.member;

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
        entity.setAddress(dto.getAddress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    public MemberDTO entityToDTO(MemberEntity entity) {
        if (entity == null)
            return null;
        final MemberDTO dto = new MemberDTO();

        dto.setMemberID(entity.getMemberID());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setStatus(entity.getStatus());

        return dto;
    }

}
