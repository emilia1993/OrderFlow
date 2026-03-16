package com.example.Ordini.model;

import com.example.Ordini.enumModel.StatoOrdine;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TestataOrdineDTO {

    private int id;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Size(max = 255, message = "La descrizione può contenere massimo 255 caratteri")
    private String descrizione;

    @NotNull(message = "La data di consegna è obbligatoria")
    @FutureOrPresent(message = "La data di consegna deve essere oggi o futura")
    private LocalDate dataConsegna;

    private StatoOrdine statoOrdine;

}

