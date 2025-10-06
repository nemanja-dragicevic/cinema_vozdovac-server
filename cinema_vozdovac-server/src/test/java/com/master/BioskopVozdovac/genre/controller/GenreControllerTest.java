package com.master.BioskopVozdovac.genre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.config.TestSecurityConfig;
import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.service.GenreService;
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

import static com.master.BioskopVozdovac.input.GenreData.GENRE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import({TestSecurityConfig.class})
class GenreControllerTest {

    @MockBean
    private GenreService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testAll() throws Exception {
        when(service.getAllGenres()).thenReturn(List.of(GENRE_DTO));

        MvcResult result = mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andReturn();
        List genres = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertNotNull(genres);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testSaveGenre() throws Exception {
        when(service.saveGenre(GENRE_DTO)).thenReturn(GENRE_DTO);

        MvcResult result = mockMvc.perform(post("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(GENRE_DTO)))
                .andExpect(status().isCreated())
                .andReturn();
        GenreDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), GenreDTO.class);
        assertNotNull(dto);
        assertEquals(GENRE_DTO.genreID(), dto.genreID());
        assertEquals(GENRE_DTO.name(), dto.name());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdateGenre() throws Exception {
        when(service.updateGenre(GENRE_DTO)).thenReturn(GENRE_DTO);

        MvcResult result = mockMvc.perform(put("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(GENRE_DTO)))
                .andExpect(status().isOk())
                .andReturn();

        GenreDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), GenreDTO.class);
        assertNotNull(dto);
        assertEquals(GENRE_DTO.genreID(), dto.genreID());
        assertEquals(GENRE_DTO.name(), dto.name());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteGenre() throws Exception {
        when(service.deleteGenre(anyLong())).thenReturn("Successfully deleted genre");

        MvcResult result = mockMvc.perform(delete("/api/genre/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Successfully deleted genre", result.getResponse().getContentAsString());
    }

}
