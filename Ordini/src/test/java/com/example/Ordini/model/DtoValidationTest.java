package com.example.Ordini.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ----------------- PagamentiDTO -----------------
    @Test
    void testPagamentiDTOValidation() {
        PagamentiDTO dto = new PagamentiDTO();
        dto.setId_ordine(null); // obbligatorio
        dto.setData_pagamento(LocalDate.now().minusDays(1)); // non valida (passata)

        Set<ConstraintViolation<PagamentiDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    // ----------------- RigheOrdineDTO -----------------
    @Test
    void testRigheOrdineDTOValidation() {
        RigheOrdineDTO dto = new RigheOrdineDTO();
        dto.setId_ordine(null);         // obbligatorio
        dto.setCod_prodotto(null);      // obbligatorio
        dto.setPrezzo(-5f);             // <0 non valido

        Set<ConstraintViolation<RigheOrdineDTO>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }

    // ----------------- TestataOrdineDTO -----------------
    @Test
    void testTestataOrdineDTOValidation() {
        TestataOrdineDTO dto = new TestataOrdineDTO();
        dto.setDescrizione("");         // vuota non valida
        dto.setDataConsegna(LocalDate.now().minusDays(1)); // passato non valido

        Set<ConstraintViolation<TestataOrdineDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }
}