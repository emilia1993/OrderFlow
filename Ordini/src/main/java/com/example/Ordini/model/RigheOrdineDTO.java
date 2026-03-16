package com.example.Ordini.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RigheOrdineDTO {

    private int id;

    @NotNull(message = "L'id dell'ordine è obbligatorio")
    private Integer id_ordine;

    @NotNull(message = "Il codice prodotto è obbligatorio")
    private Integer cod_prodotto;

    @NotNull(message = "Il prezzo è obbligatorio")
    @Min(value = 0, message = "Il prezzo deve essere maggiore o uguale a zero")
    private Float prezzo;
}
