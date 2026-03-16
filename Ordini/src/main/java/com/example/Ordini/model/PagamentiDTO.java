package com.example.Ordini.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PagamentiDTO {
    private int id;

    @NotNull(message = "La data di pagamento è obbligatoria")
    @FutureOrPresent(message = "La data di pagamento deve essere oggi o futura")
    private LocalDate data_pagamento;

    @NotNull(message = "L'id dell'ordine è obbligatorio")
    private Integer id_ordine;
}
