package com.master.BioskopVozdovac.service;

import com.master.BioskopVozdovac.member.adapter.MemberAdapter;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.repository.MemberRepository;
import com.master.BioskopVozdovac.member.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.master.BioskopVozdovac.input.MemberData.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberAdapter memberAdapter;

    @InjectMocks
    private MemberService service;

    @PersistenceContext
    EntityManager entityManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(memberAdapter.entityToDTO(MEMBER_ENTITY)).
                thenReturn(MEMBER_DTO);
    }

    @Test
    public void testRegisterMember() {
        when(memberRepository.saveAndFlush(MEMBER_ENTITY))
                .thenReturn(MEMBER_ENTITY);

        MemberDTO dto = service.create(MEMBER_ENTITY);

        assertEquals(MEMBER_DTO, dto);

        verify(memberRepository, times(1))
                .saveAndFlush(MEMBER_ENTITY);
    }

}
