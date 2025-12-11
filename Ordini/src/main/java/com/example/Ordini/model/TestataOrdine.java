package com.example.Ordini.model;

import com.example.Ordini.enumModel.StatoOrdine;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TestataOrdine {
    public int id;
    public String descrizione;
    public LocalDate dataConsegna;
    private StatoOrdine statoOrdine;

}

