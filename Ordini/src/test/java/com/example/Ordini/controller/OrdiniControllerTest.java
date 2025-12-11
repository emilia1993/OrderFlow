package com.example.Ordini.controller;

import com.example.Ordini.model.TestataOrdine;
import com.example.Ordini.repository.TestataOrdineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrdiniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestataOrdineRepository testataOrdineRepository;

    @BeforeEach
    void setUp() {
        // Puliamo il repository prima di ogni test
        testataOrdineRepository.deleteAll();
    }

    @Test
    void testVisualizzaOrdini() throws Exception {
        mockMvc.perform(get("/ordini")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreaOrdine() throws Exception {
        TestataOrdine ordine = new TestataOrdine();
        ordine.setDescrizione("Ordine integrazione");
        ordine.setDataConsegna(LocalDate.now());

        mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.descrizione").value("Ordine integrazione"));
    }

    @Test
    void testCancellaOrdineEsistente() throws Exception {
        // Prima creiamo un ordine
        TestataOrdine ordine = new TestataOrdine();
        ordine.setDescrizione("Ordine da cancellare");
        ordine.setDataConsegna(LocalDate.now());

        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TestataOrdine created = objectMapper.readValue(response, TestataOrdine.class);

        // Ora facciamo DELETE
        mockMvc.perform(delete("/ordini/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("cancellato correttamente")));
    }

    @Test
    void testCancellaOrdineNonEsistente() throws Exception {
        mockMvc.perform(delete("/ordini/{id}", 9999))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("non trovato")));
    }
}
