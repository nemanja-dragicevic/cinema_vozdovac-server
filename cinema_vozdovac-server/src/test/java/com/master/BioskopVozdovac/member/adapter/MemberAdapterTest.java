package com.master.BioskopVozdovac.member.adapter;

import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import org.junit.jupiter.api.Test;

import static com.master.BioskopVozdovac.input.MemberData.MEMBER_DTO;
import static com.master.BioskopVozdovac.input.MemberData.MEMBER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MemberAdapterTest {

    @Test
    void testDTOToEntity() {
        MemberEntity entity = MemberAdapter.dtoToEntity(MEMBER_DTO);

        assertNotNull(entity);
        assertEquals(entity.getMemberID(), MEMBER_DTO.getMemberID());
        assertEquals(entity.getGender(), MEMBER_DTO.getGender());
        assertEquals(entity.getRole(), MEMBER_DTO.getRole());
        assertEquals(entity.getBirthDate(), MEMBER_DTO.getBirthDate());
        assertEquals(entity.getEmail(), MEMBER_DTO.getEmail());
        assertEquals(entity.getFirstName(), MEMBER_DTO.getFirstName());
        assertEquals(entity.getLastName(), MEMBER_DTO.getLastName());
    }

    @Test
    void testEntityToDTO() {
        MemberDTO dto = MemberAdapter.entityToDTO(MEMBER_ENTITY);

        assertNotNull(dto);
        assertEquals(dto.getMemberID(), MEMBER_ENTITY.getMemberID());
        assertEquals(dto.getGender(), MEMBER_ENTITY.getGender());
        assertEquals(dto.getRole(), MEMBER_ENTITY.getRole());
        assertEquals(dto.getBirthDate(), MEMBER_ENTITY.getBirthDate());
        assertEquals(dto.getEmail(), MEMBER_ENTITY.getEmail());
        assertEquals(dto.getFirstName(), MEMBER_ENTITY.getFirstName());
        assertEquals(dto.getLastName(), MEMBER_ENTITY.getLastName());
    }

}
