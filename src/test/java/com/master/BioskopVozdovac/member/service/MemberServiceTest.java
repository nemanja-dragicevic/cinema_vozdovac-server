package com.master.BioskopVozdovac.member.service;

import com.master.BioskopVozdovac.exception.UserException;
import com.master.BioskopVozdovac.member.adapter.MemberAdapter;
import com.master.BioskopVozdovac.member.model.ChangePassword;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import com.master.BioskopVozdovac.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.master.BioskopVozdovac.input.MemberData.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberAdapter memberAdapter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(memberAdapter.entityToDTO(MEMBER_ENTITY)).
                thenReturn(MEMBER_DTO);
    }

    @Test
    void testCreate() {
        when(memberRepository.saveAndFlush(any(MemberEntity.class))).thenReturn(MEMBER_ENTITY);

        MemberDTO result = service.create(MEMBER_ENTITY);

        assertNotNull(result);
        assertEquals(result.getUsername(), MEMBER_DTO.getUsername());
        verify(memberRepository, times(1)).saveAndFlush(any(MemberEntity.class));
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    public void testRegisterMember() {
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(CharBuffer.class))).thenReturn("encoded");
        when(memberRepository.saveAndFlush(MEMBER_ENTITY))
                .thenReturn(MEMBER_ENTITY);
        when(memberAdapter.dtoToEntity(any(MemberDTO.class))).thenReturn(MEMBER_ENTITY);

        MemberDTO dto = service.register(MEMBER_DTO);

        assertNotNull(dto);
        assertNotNull(dto.getPassword());
        assertEquals(MEMBER_DTO, dto);

        verify(memberRepository, times(1))
                .saveAndFlush(MEMBER_ENTITY);
        verify(passwordEncoder, times(1)).encode(any(CharBuffer.class));
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(memberAdapter, times(1)).dtoToEntity(any(MemberDTO.class));
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testRegisterUsernameTaken() {
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(MEMBER_ENTITY));

        UserException exception = assertThrows(UserException.class, () -> service.register(MEMBER_DTO));

        assertEquals("Username already taken", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(0)).encode(any(CharBuffer.class));
        verify(memberRepository, times(0)).saveAndFlush(any(MemberEntity.class));
    }

    @Test
    void testGetDTOById() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(MEMBER_ENTITY));

        MemberDTO result = service.getDtoById(0L);

        assertNotNull(result);
        assertEquals(MEMBER_DTO.getUsername(), result.getUsername());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testGetDTOByIdNotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> service.getDtoById(1L));

        assertEquals("There is no member with id: 1", exception.getMessage());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(memberAdapter, times(0)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testUpdateMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(MEMBER_ENTITY));
        when(memberAdapter.dtoToEntity(any(MemberDTO.class))).thenReturn(MEMBER_ENTITY);
        when(memberRepository.saveAndFlush(any(MemberEntity.class))).thenReturn(MEMBER_ENTITY);

        MemberDTO result = service.updateMember(MEMBER_DTO);

        assertNotNull(result);
        assertEquals(MEMBER_DTO.getUsername(), result.getUsername());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).saveAndFlush(any(MemberEntity.class));
        verify(memberAdapter, times(1)).dtoToEntity(any(MemberDTO.class));
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testUpdateMember_NotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        MEMBER_DTO.setMemberID(1L);
        UserException exception = assertThrows(UserException.class, () -> service.updateMember(MEMBER_DTO));

        assertEquals("Member with id: 1 doesn't exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(0)).saveAndFlush(any(MemberEntity.class));
    }

    @Test
    void testDeleteMember() {
        doNothing().when(memberRepository).deleteById(anyLong());

        String result = service.deleteMember(1L);

        assertEquals("Successfully deleted member with id: 1", result);
        verify(memberRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testLogin() {
        MEMBER_ENTITY.setPassword("password123_?!");
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(MEMBER_ENTITY));
        when(passwordEncoder.matches(any(CharBuffer.class), anyString())).thenReturn(true);
        when(memberAdapter.entityToDTO(MEMBER_ENTITY)).thenReturn(NO_PASS_MEMBER_DTO);

        MemberDTO result = service.login(MEMBER_DTO);

        assertNotNull(result);
        assertNull(result.getPassword());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).matches(any(CharBuffer.class), anyString());
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testLogin_UserNotFound() {
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> service.login(MEMBER_DTO));

        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(0)).matches(any(CharBuffer.class), anyString());
        verify(memberAdapter, times(0)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testLogin_WrongPassword() {
        MEMBER_ENTITY.setPassword("password123_?!");
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(MEMBER_ENTITY));
        when(passwordEncoder.matches(any(CharBuffer.class), anyString())).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> service.login(MEMBER_DTO));

        assertEquals("Wrong password", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).matches(any(CharBuffer.class), anyString());
        verify(memberAdapter, times(0)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testFindByUsername() {
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(MEMBER_ENTITY));
        when(memberAdapter.entityToDTO(any(MemberEntity.class))).thenReturn(MEMBER_DTO);

        MemberDTO result = service.findByUsername("test_user");

        assertNotNull(result);
        assertEquals(MEMBER_DTO.getUsername(), result.getUsername());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testFindByUsername_NotFound() {
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> service.findByUsername("test_user"));

        assertEquals("There is no such username", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(memberAdapter, times(0)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testChangePassword() {
        ChangePassword changePassword = new ChangePassword();
        changePassword.setMember(MEMBER_DTO);
        changePassword.setOldPassword("oldPassword");
        changePassword.setNewPassword("newPassword");

        MEMBER_ENTITY.setPassword("password123_?!");
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(MEMBER_ENTITY));
        when(passwordEncoder.matches(any(CharBuffer.class), anyString())).thenReturn(true);
        when(passwordEncoder.encode(any(CharBuffer.class))).thenReturn("encodedNewPassword");
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(MEMBER_ENTITY);
        when(memberAdapter.entityToDTO(any(MemberEntity.class))).thenReturn(MEMBER_DTO);

        MemberDTO result = service.changePassword(changePassword);

        assertNotNull(result);
        assertEquals(MEMBER_DTO.getUsername(), result.getUsername());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).encode(any(CharBuffer.class));
        verify(passwordEncoder, times(1)).matches(any(CharBuffer.class), anyString());
        verify(memberRepository, times(1)).save(any(MemberEntity.class));
        verify(memberAdapter, times(1)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testChangePasswordUserNotFound() {
        ChangePassword changePassword = new ChangePassword();
        changePassword.setMember(MEMBER_DTO);
        changePassword.setOldPassword("oldPassword");
        changePassword.setNewPassword("newPassword");

        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> service.changePassword(changePassword));

        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(0)).encode(any(CharBuffer.class));
        verify(passwordEncoder, times(0)).matches(any(CharBuffer.class), anyString());
        verify(memberRepository, times(0)).save(any(MemberEntity.class));
        verify(memberAdapter, times(0)).entityToDTO(any(MemberEntity.class));
    }

    @Test
    void testChangePasswordWrongOldPassword() {
        ChangePassword changePassword = new ChangePassword();
        changePassword.setMember(MEMBER_DTO);
        changePassword.setOldPassword("oldPassword");
        changePassword.setNewPassword("newPassword");

        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(MEMBER_ENTITY));
        when(passwordEncoder.matches(any(CharBuffer.class), anyString())).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> service.changePassword(changePassword));

        assertEquals("Wrong password", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).matches(any(CharBuffer.class), anyString());
        verify(passwordEncoder, times(0)).encode(any(CharBuffer.class));
        verify(memberRepository, times(0)).save(any(MemberEntity.class));
        verify(memberAdapter, times(0)).entityToDTO(any(MemberEntity.class));
    }

}
