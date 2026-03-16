package com.example.Ordini.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "righe_ordine")
public class RigheOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Collegamento all'ordine
    @ManyToOne
    @JoinColumn(name = "id_ordine", nullable = false)
    private TestataOrdine ordine;

    @Column(name = "cod_prodotto")
    private Integer codProdotto;

    private BigDecimal prezzo;
}

