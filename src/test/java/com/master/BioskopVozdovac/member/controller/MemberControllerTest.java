package com.master.BioskopVozdovac.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.config.TestSecurityConfig;
import com.master.BioskopVozdovac.member.model.ChangePassword;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.service.MemberService;
import com.master.BioskopVozdovac.security.MemberAuthenticationProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.master.BioskopVozdovac.input.MemberData.MEMBER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import({TestSecurityConfig.class})
class MemberControllerTest {

    @MockBean
    private MemberService service;

    @MockBean
    private MemberAuthenticationProvider provider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegister() throws Exception {
        when(service.register(any(MemberDTO.class))).thenReturn(MEMBER_DTO);
        when(provider.createToken(any(MemberDTO.class))).thenReturn("123");

        MvcResult result = mockMvc.perform(post("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(MEMBER_DTO)))
                .andExpect(status().isOk())
                .andReturn();
        MemberDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), MemberDTO.class);

        assertNotNull(dto);
        assertNotNull(dto.getToken());
        assertEquals(MEMBER_DTO.getUsername(), dto.getUsername());
        assertEquals(MEMBER_DTO.getEmail(), dto.getEmail());
        assertEquals(MEMBER_DTO.getFirstName(), dto.getFirstName());
        assertEquals(MEMBER_DTO.getLastName(), dto.getLastName());
    }

    @Test
    void testLogin() throws Exception {
        when(service.login(any(MemberDTO.class))).thenReturn(MEMBER_DTO);
        when(provider.createToken(any(MemberDTO.class))).thenReturn("123");

        MvcResult result = mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MEMBER_DTO)))
                .andExpect(status().isOk())
                .andReturn();
        MemberDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), MemberDTO.class);

        assertNotNull(dto);
        assertNotNull(dto.getToken());
        assertEquals(MEMBER_DTO.getUsername(), dto.getUsername());
        assertEquals(MEMBER_DTO.getEmail(), dto.getEmail());
        assertEquals(MEMBER_DTO.getFirstName(), dto.getFirstName());
        assertEquals(MEMBER_DTO.getLastName(), dto.getLastName());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testUpdateUser() throws Exception {
        when(service.updateMember(any(MemberDTO.class))).thenReturn(MEMBER_DTO);

        MvcResult result = mockMvc.perform(put("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(MEMBER_DTO)))
                .andExpect(status().isOk())
                .andReturn();
        MemberDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), MemberDTO.class);

        assertNotNull(dto);
        assertEquals(MEMBER_DTO.getUsername(), dto.getUsername());
        assertEquals(MEMBER_DTO.getEmail(), dto.getEmail());
        assertEquals(MEMBER_DTO.getFirstName(), dto.getFirstName());
        assertEquals(MEMBER_DTO.getLastName(), dto.getLastName());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testChangePassword() throws Exception {
        ChangePassword changePasswordItem = new ChangePassword(MEMBER_DTO,
                "123", "456");
        when(service.changePassword(any(ChangePassword.class))).thenReturn(MEMBER_DTO);
        MvcResult result = mockMvc.perform(put("/api/members/change-pass")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordItem)))
                .andExpect(status().isOk())
                .andReturn();

        MemberDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), MemberDTO.class);
        assertNotNull(dto);
        assertEquals(MEMBER_DTO.getUsername(), dto.getUsername());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testDeleteUser() throws Exception {
        when(service.deleteMember(anyLong())).thenReturn("Successfully deleted member with id: 1");

        MvcResult result = mockMvc.perform(delete("/api/members/1"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertEquals("Successfully deleted member with id: 1", content);
    }

}
