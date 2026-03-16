package com.example.Ordini.controller;

import com.example.Ordini.model.TestataOrdineDTO;
import com.example.Ordini.repository.TestataOrdineRepository;
import com.example.Ordini.enumModel.StatoOrdine;
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
        testataOrdineRepository.deleteAll();
    }

    // Test per visualizzare tutti gli ordini
    @Test
    void testVisualizzaOrdini() throws Exception {
        mockMvc.perform(get("/ordini").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // Test per creare un nuovo ordine
    @Test
    void testCreaOrdine() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine integrazione");
        ordine.setDataConsegna(LocalDate.now());

        mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.descrizione").value("Ordine integrazione"));
    }

    // Test per creare un ordine con dati errati
    @Test
    void testCreaOrdineConDatiErrati() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("");  // Descrizione vuota
        ordine.setDataConsegna(null); // Data di consegna nulla

        mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isBadRequest())  // 400
                .andExpect(jsonPath("$.descrizione").value("La descrizione è obbligatoria"))
                .andExpect(jsonPath("$.dataConsegna").value("La data di consegna è obbligatoria"));
    }

    // Test per aggiornare un ordine esistente
    @Test
    void testAggiornaOrdine() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine da aggiornare");
        ordine.setDataConsegna(LocalDate.now());

        // Creazione dell'ordine
        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TestataOrdineDTO created = objectMapper.readValue(response, TestataOrdineDTO.class);

        // Aggiornamento dell'ordine
        created.setDescrizione("Ordine aggiornato");
        mockMvc.perform(put("/ordini/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descrizione").value("Ordine aggiornato"));
    }

    // Test per aggiornare lo stato di un ordine
    @Test
    void testAggiornaStatoOrdine() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine da aggiornare");
        ordine.setDataConsegna(LocalDate.now());

        // Creazione dell'ordine
        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TestataOrdineDTO created = objectMapper.readValue(response, TestataOrdineDTO.class);

        // Aggiornamento dello stato dell'ordine
        StatoOrdine nuovoStato = StatoOrdine.SPEDITO;
        mockMvc.perform(patch("/ordini/{id}/stato", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovoStato)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statoOrdine").value("SPEDITO"));
    }

    // Test per cancellare un ordine esistente
    @Test
    void testCancellaOrdineEsistente() throws Exception {
        // Creo un nuovo ordine
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine da cancellare");
        ordine.setDataConsegna(LocalDate.now());

        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TestataOrdineDTO created = objectMapper.readValue(response, TestataOrdineDTO.class);

        // Cancello l'ordine esistente e verifico 204
        mockMvc.perform(delete("/ordini/{id}", created.getId()))
                .andExpect(status().isNoContent());  // Cambiato da isOk() a isNoContent()
    }

    // Test per cancellare un ordine non esistente
    @Test
    void testCancellaOrdineNonEsistente() throws Exception {
        mockMvc.perform(delete("/ordini/{id}", 9999))  // ID non esistente
                .andExpect(status().isNotFound())
                .andExpect(content().string("Risorsa non trovata: Ordine con id 9999 non trovato."));
    }

    // Test per visualizzare tutti gli ordini (assicurarsi che la risposta sia un array)
    @Test
    void testVisualizzaOrdiniFormato() throws Exception {
        mockMvc.perform(get("/ordini").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}