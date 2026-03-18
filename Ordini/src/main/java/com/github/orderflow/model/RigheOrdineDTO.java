package com.github.orderflow.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RigheOrdineDTO {

    private int id;

    @NotNull(message = "Order ID is required")
    private Integer id_ordine;

    @NotNull(message = "Product code is required")
    private Integer cod_prodotto;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be zero or positive")
    private Float prezzo;
}
