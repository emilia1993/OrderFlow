package com.example.Ordini.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Pagamenti {
    public int id;
    public LocalDate data_pagamento;
    public int id_ordine;
}
