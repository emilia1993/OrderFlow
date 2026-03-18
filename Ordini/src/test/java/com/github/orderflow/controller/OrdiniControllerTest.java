package com.github.orderflow.controller;

import com.github.orderflow.exception.GlobalExceptionHandler;
import com.github.orderflow.model.TestataOrdineDTO;
import com.github.orderflow.enumModel.StatoOrdine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrdiniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Test to display all orders
    @Test
    void testVisualizzaOrdini() throws Exception {
        mockMvc.perform(get("/ordini").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // Test to create a new order
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

    // Test to create an order with invalid data
    @Test
    void testCreaOrdineConDatiErrati() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("");  // Empty description
        ordine.setDataConsegna(null); // Null delivery date

        mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isBadRequest())  // 400
                .andExpect(jsonPath("$.descrizione").value("Description is required"))
                .andExpect(jsonPath("$.dataConsegna").value("Delivery date is required"));
    }

    // Test to update an existing order
    @Test
    void testAggiornaOrdine() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine da aggiornare");
        ordine.setDataConsegna(LocalDate.now());

        // Order creation
        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TestataOrdineDTO created = objectMapper.readValue(response, TestataOrdineDTO.class);

        // Order update
        created.setDescrizione("Ordine aggiornato");
        mockMvc.perform(put("/ordini/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descrizione").value("Ordine aggiornato"));
    }

    // Test to update an order status
    @Test
    void testAggiornaStatoOrdine() throws Exception {
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine da aggiornare");
        ordine.setDataConsegna(LocalDate.now());

        // Order creation
        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TestataOrdineDTO created = objectMapper.readValue(response, TestataOrdineDTO.class);

        // Order status update
        StatoOrdine nuovoStato = StatoOrdine.SPEDITO;
        mockMvc.perform(patch("/ordini/{id}/stato", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuovoStato)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statoOrdine").value("SPEDITO"));
    }

    // Test to delete an existing order
    @Test
    void testCancellaOrdineEsistente() throws Exception {
        // Create a new order
        TestataOrdineDTO ordine = new TestataOrdineDTO();
        ordine.setDescrizione("Ordine da cancellare");
        ordine.setDataConsegna(LocalDate.now());

        String response = mockMvc.perform(post("/ordini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordine)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TestataOrdineDTO created = objectMapper.readValue(response, TestataOrdineDTO.class);

        // Delete the existing order and verify 204
        mockMvc.perform(delete("/ordini/{id}", created.getId()))
                .andExpect(status().isNoContent());  // Cambiato da isOk() a isNoContent()
    }

    // Test to delete a non-existing order
    @Test
    void testCancellaOrdineNonEsistente() throws Exception {
        mockMvc.perform(delete("/ordini/{id}", 9999))  // ID non esistente
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Resource not found: Order with id 9999 not found."));
    }

    // Test to retrieve all orders (ensure the response is an array)
    @Test
    void testVisualizzaOrdiniFormato() throws Exception {
        mockMvc.perform(get("/ordini").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}