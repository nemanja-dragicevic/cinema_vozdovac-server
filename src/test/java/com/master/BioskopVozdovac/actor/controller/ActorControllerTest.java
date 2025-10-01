package com.master.BioskopVozdovac.actor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.service.ActorService;
import com.master.BioskopVozdovac.config.TestSecurityConfig;
import com.master.BioskopVozdovac.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.master.BioskopVozdovac.input.ActorData.ACTOR_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorController.class)
@Import({TestSecurityConfig.class})
class ActorControllerTest {

    private static final String ADMIN_USER = "ADMIN";

    @MockBean
    private ActorService actorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testGetAll() throws Exception {
        when(actorService.getAll()).thenReturn(List.of(ACTOR_DTO));

        MvcResult result = mockMvc.perform(get("/api/actor/all"))
                .andExpect(status().isOk())
                .andReturn();
        List dtos = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertNotNull(dtos);
    }

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testSaveActor() throws Exception {
        when(actorService.saveActor(any(ActorDTO.class))).thenReturn(ACTOR_DTO);

        MvcResult result = mockMvc.perform(post("/api/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ACTOR_DTO)))
                .andExpect(status().isCreated()) // This will fail, but we'll see the error
                .andReturn();

        ActorDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), ActorDTO.class);
        assertNotNull(dto);
        assertEquals(dto.firstName(), ACTOR_DTO.firstName());
        assertEquals(dto.lastName(), ACTOR_DTO.lastName());
        assertEquals(dto.gender(), ACTOR_DTO.gender());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testSaveActor_Forbidden() throws Exception {
        mockMvc.perform(post("/api/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ACTOR_DTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testGetActorById() throws Exception {
        when(actorService.getActorById(anyLong())).thenReturn(ACTOR_DTO);

        mockMvc.perform(get("/api/actor/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testGetActorById_NotExists() throws Exception {
        when(actorService.getActorById(anyLong())).thenThrow(new NotFoundException("Could not find actor with id: 1"));

        mockMvc.perform(get("/api/actor/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testGetActorById_USER() throws Exception {
        when(actorService.getActorById(anyLong())).thenReturn(ACTOR_DTO);

        mockMvc.perform(get("/api/actor/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testUpdateActor() throws Exception {
        when(actorService.updateActor(ACTOR_DTO)).thenReturn(ACTOR_DTO);

        MvcResult result = mockMvc.perform(put("/api/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ACTOR_DTO)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(ACTOR_DTO));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testUpdateActor_USER() throws Exception {
        mockMvc.perform(put("/api/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ACTOR_DTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testDeleteActor() throws Exception {
        when(actorService.deleteActorById(anyLong())).thenReturn("Successfully deleted actor");

        MvcResult result = mockMvc.perform(delete("/api/actor/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Successfully deleted actor", result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testDeleteActor_USER() throws Exception {
        mockMvc.perform(delete("/api/actor/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = ADMIN_USER)
    void testDeleteActor_NotExists() throws Exception {
        when(actorService.deleteActorById(anyLong())).thenThrow(new NotFoundException("Could not find actor with id: 1"));

        mockMvc.perform(delete("/api/actor/1")).andExpect(status().isNotFound());
    }

}
