package com.example.Ordini.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pagamenti")
public class Pagamenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    // Collegamento all'ordine
    @ManyToOne
    @JoinColumn(name = "id_ordine", nullable = false)
    private TestataOrdine ordine;
}
