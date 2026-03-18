package com.github.orderflow.model;

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

    // PagamentiDTO
    @Test
    void testPagamentiDTOValidation() {
        PagamentiDTO dto = new PagamentiDTO();
        dto.setId_ordine(null); // Required
        dto.setData_pagamento(LocalDate.now().minusDays(1)); // Not valid (date in the past)

        Set<ConstraintViolation<PagamentiDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    // RigheOrdineDTO
    @Test
    void testRigheOrdineDTOValidation() {
        RigheOrdineDTO dto = new RigheOrdineDTO();
        dto.setId_ordine(null);         // Required
        dto.setCod_prodotto(null);      // Required
        dto.setPrezzo(-5f);             // < 0 is invalid

        Set<ConstraintViolation<RigheOrdineDTO>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }

    // TestataOrdineDTO
    @Test
    void testTestataOrdineDTOValidation() {
        TestataOrdineDTO dto = new TestataOrdineDTO();
        dto.setDescrizione("");         // Empty value is not valid
        dto.setDataConsegna(LocalDate.now().minusDays(1)); // Past date is not valid

        Set<ConstraintViolation<TestataOrdineDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }
}