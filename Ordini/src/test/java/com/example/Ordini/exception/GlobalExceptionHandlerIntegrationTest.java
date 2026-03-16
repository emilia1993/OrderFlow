package com.example.Ordini.exception;

import com.example.Ordini.model.TestataOrdineDTO;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GlobalExceptionHandlerTest.TestControllerForException.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    // Controller di prova interno per generare eccezioni
    @RestController
    static class TestControllerForException {

        @GetMapping("/illegal-argument")
        public void throwIllegalArgumentException() {
            throw new IllegalArgumentException("ID non trovato");
        }

        @GetMapping("/generic-exception")
        public void throwGenericException() throws Exception {
            throw new Exception("Errore generico");
        }

        @PostMapping("/validation")
        public void throwValidationException(@RequestBody @Valid TestataOrdineDTO dto) {
            // solo per testare la validazione DTO
        }
    }

    @Test
    void testHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/illegal-argument")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Risorsa non trovata: ID non trovato"));
    }

    @Test
    void testHandleGenericException() throws Exception {
        mockMvc.perform(get("/generic-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Errore interno: Errore generico"));
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // DTO vuoto per scatenare errori di validazione
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}