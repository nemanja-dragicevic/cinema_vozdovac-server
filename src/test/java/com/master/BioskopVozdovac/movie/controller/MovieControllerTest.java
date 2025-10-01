package com.master.BioskopVozdovac.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.config.TestSecurityConfig;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.master.BioskopVozdovac.input.MovieData.MOVIE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@Import({TestSecurityConfig.class})
class MovieControllerTest {

    @MockBean
    private MovieService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testSaveMovie() throws Exception {
        when(service.saveMovie(any(MovieDTO.class), any(MultipartFile.class), any(MultipartFile.class)))
                .thenReturn(MOVIE_DTO);

        String json = objectMapper.writeValueAsString(MOVIE_DTO);
        MvcResult result = mockMvc.perform(multipart("/api/movie")
                        .file(new MockMultipartFile("smallPicture", "small.jpg", "image/jpeg", "small picture".getBytes()))
                        .file(new MockMultipartFile("bigPicture", "big.jpg", "image/jpeg", "big picture".getBytes()))
                        .param("movie", json)
                        .with(SecurityMockMvcRequestPostProcessors.user("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isCreated())
                .andReturn();
        MovieDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), MovieDTO.class);
        assertNotNull(dto);
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testGetMovieById() throws Exception {
        when(service.getMovieById(anyLong())).thenReturn(MOVIE_DTO);

        MvcResult result = mockMvc.perform(get("/api/movie/1"))
                .andExpect(status().isOk())
                .andReturn();
        MovieDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), MovieDTO.class);
        assertNotNull(dto);
        assertEquals(MOVIE_DTO.getName(), dto.getName());
        assertEquals(MOVIE_DTO.getDescription(), dto.getDescription());
        assertEquals(MOVIE_DTO.getDuration(), dto.getDuration());
        assertEquals(MOVIE_DTO.getStartTime(), dto.getStartTime());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteMovieById() throws Exception {
        when(service.deleteMovieById(anyLong())).thenReturn("Successfully deleted movie with id: 1");

        MvcResult result = mockMvc.perform(delete("/api/movie/1"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertEquals("Successfully deleted movie with id: 1", content);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAllMoviesAWS() throws Exception {
        when(service.getAllMoviesWithAWS()).thenReturn(List.of(MOVIE_DTO));

        MvcResult result = mockMvc.perform(get("/api/movie/allWithAWS"))
                .andExpect(status().isOk())
                .andReturn();
        List movies = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertNotNull(movies);
        assertEquals(1, movies.size());
    }

}
