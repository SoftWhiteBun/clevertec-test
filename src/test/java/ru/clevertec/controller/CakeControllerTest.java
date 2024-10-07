package ru.clevertec.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.common.CakeType;
import ru.clevertec.domain.Cake;
import ru.clevertec.service.CakeService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CakeController.class)
class CakeControllerTest {
    @MockBean
    private CakeService cakeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAll() throws Exception {
        //given
        when(cakeService.getCakes()).thenReturn(List.of(new Cake(UUID.randomUUID(),"cake1", CakeType.SHOP, OffsetDateTime.now()) ,
                new Cake(UUID.randomUUID(),"cake2", CakeType.PASTRY, OffsetDateTime.now().plusDays(3)),
                new Cake(UUID.randomUUID(),"cake3", CakeType.HOMEMADE, OffsetDateTime.now())));

        //when, then
        mockMvc.perform(get("/api/v1/cakes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void shouldGetCakeById() throws Exception {
        //given
        UUID cakeId = UUID.randomUUID();
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        when(cakeService.getCakeById(cakeId)).thenReturn(cake);

        //when, then
        mockMvc.perform(get("/api/v1/cakes/{cakeId}", cakeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cakeId.toString()))
                .andExpect(jsonPath("$.title").value("cake"));
    }

    @Test
    void shouldCreateNewCake() throws Exception {
        //given
        UUID cakeId = UUID.randomUUID();
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        when(cakeService.create(cake)).thenReturn(cake);

        //when,then
        mockMvc.perform(post("/api/v1/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cake)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateCake() throws Exception {
        //given
        UUID cakeId = UUID.randomUUID();
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        when(cakeService.update(cakeId, cake)).thenReturn(cake);

        //when, then
        mockMvc.perform(put("/api/v1/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cake)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCakeById() throws Exception {
        //given
        UUID cakeId = UUID.randomUUID();
        doNothing().when(cakeService).deleteById(cakeId);

        //when, then
        mockMvc.perform(delete("/api/v1/cakes/{cakeId}", cakeId))
                .andExpect(status().isOk());
    }
}