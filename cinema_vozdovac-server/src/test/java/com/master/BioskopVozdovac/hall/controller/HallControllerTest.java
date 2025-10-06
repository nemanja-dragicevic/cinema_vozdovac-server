package com.master.BioskopVozdovac.hall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.BioskopVozdovac.config.TestSecurityConfig;
import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.service.HallService;
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

import static com.master.BioskopVozdovac.input.HallData.CREATE_HALL;
import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HallController.class)
@Import({TestSecurityConfig.class})
class HallControllerTest {

    @MockBean
    private HallService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(HALL_DTO));

        MvcResult result = mockMvc.perform(get("/api/hall"))
                .andExpect(status().isOk())
                .andReturn();
        List dto = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertNotNull(dto);
        assertEquals(1, dto.size());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testCreate() throws Exception {
        when(service.createHall(CREATE_HALL)).thenReturn(HALL_DTO);

        MvcResult result = mockMvc.perform(post("/api/hall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(HALL_DTO)))
                .andExpect(status().isCreated())
                .andReturn();
        HallDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), HallDTO.class);
        assertNotNull(dto);
        assertEquals(HALL_DTO.hallID(), dto.hallID());
        assertEquals(HALL_DTO.hallName(), dto.hallName());
        assertEquals(HALL_DTO.rowsCount(), dto.rowsCount());
        assertEquals(HALL_DTO.seatsPerRow(), dto.seatsPerRow());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdate() throws Exception {
        when(service.updateHall(HALL_DTO)).thenReturn(HALL_DTO);

        MvcResult result = mockMvc.perform(put("/api/hall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(HALL_DTO)))
                .andExpect(status().isOk())
                .andReturn();
        HallDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), HallDTO.class);
        assertNotNull(dto);
        assertEquals(HALL_DTO.hallID(), dto.hallID());
        assertEquals(HALL_DTO.hallName(), dto.hallName());
        assertEquals(HALL_DTO.rowsCount(), dto.rowsCount());
        assertEquals(HALL_DTO.seatsPerRow(), dto.seatsPerRow());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDelete() throws Exception {
        when(service.deleteWithId(anyLong())).thenReturn("Successfully deleted hall");

        MvcResult result = mockMvc.perform(delete("/api/hall/1"))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("Successfully deleted hall", content);
    }

}
