package com.github.orderflow.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PagamentiDTO {
    private int id;

    @NotNull(message = "Payment date is required")
    @FutureOrPresent(message = "Payment date must be today or a future date")
    private LocalDate dataPagamento;

    @NotNull(message = "Order ID is required")
    private Integer idOrdine;
}
