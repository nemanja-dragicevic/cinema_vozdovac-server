package com.master.BioskopVozdovac.member.adapter;

import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;

public final class MemberAdapter {

    private MemberAdapter(){
        throw new AssertionError();
    }

    public static MemberEntity dtoToEntity(MemberDTO dto) {
        if(dto == null)
            return null;

        return MemberEntity.builder()
                .memberID(dto.getMemberID())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .role(dto.getRole())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public static MemberDTO entityToDTO(MemberEntity entity) {
        if (entity == null)
            return null;
        final MemberDTO dto = new MemberDTO();

        dto.setMemberID(entity.getMemberID());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());

        return dto;
    }

}
