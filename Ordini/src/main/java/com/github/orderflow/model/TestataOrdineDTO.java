package com.github.orderflow.model;

import com.github.orderflow.enumModel.StatoOrdine;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TestataOrdineDTO {

    private int id;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String descrizione;

    @NotNull(message = "Delivery date is required")
    @FutureOrPresent(message = "Delivery date must be today or a future date")
    private LocalDate dataConsegna;

    private StatoOrdine statoOrdine;

}

