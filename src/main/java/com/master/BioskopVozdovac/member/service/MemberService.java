package com.master.BioskopVozdovac.member.service;

import com.master.BioskopVozdovac.enums.MemberRole;
import com.master.BioskopVozdovac.exception.NotFoundException;
import com.master.BioskopVozdovac.exception.UserException;
import com.master.BioskopVozdovac.exception.UsernamePasswordException;
import com.master.BioskopVozdovac.member.adapter.MemberAdapter;
import com.master.BioskopVozdovac.member.model.ChangePassword;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import com.master.BioskopVozdovac.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.CharBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final String USERNAME_PASSWORD_ERROR = "Wrong username or password";

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO create(MemberEntity entity) {
        return MemberAdapter.entityToDTO(memberRepository.save(entity));
    }

    /**
     * Registers a new member based on the provided MemberDTO.
     * This method checks if the username is already taken, encodes the password using
     * the password encoder, assigns a default role to the member, saves the member entity
     * to the database, and returns the corresponding MemberDTO.
     *
     * @param dto The MemberDTO containing registration details.
     *
     * @return The registered MemberDTO without the password.
     *
     * @throws UserException if the username provided in the DTO is already taken.
     */
    public MemberDTO register(MemberDTO dto) {
        Optional<MemberEntity> entities = memberRepository.findByUsername(dto.getUsername());

        if (entities.isPresent())
            throw new UserException("Username already taken");

        dto.setPassword(passwordEncoder.encode(CharBuffer.wrap(dto.getPassword())));
        dto.setRole(MemberRole.USER);

        MemberEntity entity = memberRepository.save(MemberAdapter.dtoToEntity(dto));
        entity.setPassword(null);

        return MemberAdapter.entityToDTO(entity);
    }

    public MemberDTO getDtoById(Long id) {
        MemberEntity entity = memberRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("There is no member with id: " + id));
        return MemberAdapter.entityToDTO(entity);
    }

    public MemberDTO updateMember(@RequestBody MemberDTO dto) {
        MemberEntity entity = memberRepository.findById(dto.getMemberID()).orElseThrow(()
                    -> new NotFoundException("Member with id: " + dto.getMemberID() + " doesn't exist"));
        MemberEntity updated = MemberAdapter.dtoToEntity(dto);
        updated.setPassword(entity.getPassword());
        return MemberAdapter.entityToDTO(memberRepository.saveAndFlush(updated));
    }

    public String deleteMember(Long id) {
        memberRepository.deleteById(id);
        return "Successfully deleted member with id: " + id;
    }

    public MemberDTO login(MemberDTO dto) {
        MemberEntity entity = memberRepository.findByUsername(dto.getUsername()).
                orElseThrow(() -> new UsernamePasswordException(USERNAME_PASSWORD_ERROR));

        if (!passwordEncoder.matches(CharBuffer.wrap(dto.getPassword()), entity.getPassword()))
            throw new UsernamePasswordException(USERNAME_PASSWORD_ERROR);

        entity.setPassword(null);
        return MemberAdapter.entityToDTO(entity);
    }

    public MemberDTO findByUsername(String username) {
        MemberEntity member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernamePasswordException(USERNAME_PASSWORD_ERROR));
        return MemberAdapter.entityToDTO(member);
    }

    public MemberDTO changePassword(ChangePassword dto) {
        MemberEntity entity = memberRepository.findByUsername(dto.getMember().getUsername()).
                orElseThrow(() -> new UsernamePasswordException(USERNAME_PASSWORD_ERROR));

        if (!passwordEncoder.matches(CharBuffer.wrap(dto.getOldPassword()), entity.getPassword()))
            throw new UsernamePasswordException(USERNAME_PASSWORD_ERROR);

        entity.setPassword(passwordEncoder.encode(CharBuffer.wrap(dto.getNewPassword())));

        return MemberAdapter.entityToDTO(memberRepository.save(entity));
    }
}
