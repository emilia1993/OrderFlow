package com.github.orderflow.exception;

import com.github.orderflow.model.TestataOrdineDTO;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @RestController
    static class TestController {

        @GetMapping("/illegal-argument")
        public void illegal() {
            throw new IllegalArgumentException("ID non trovato");
        }

        @GetMapping("/generic-exception")
        public void generic() throws Exception {
            throw new Exception("Errore generico");
        }

        @PostMapping("/validation")
        public void validation(@RequestBody @Valid TestataOrdineDTO dto) {}
    }

    @Test
    void testIllegalArgument() throws Exception {
        mockMvc.perform(get("/illegal-argument"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGenericException() throws Exception {
        mockMvc.perform(get("/generic-exception"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testValidationException() throws Exception {
        mockMvc.perform(post("/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}