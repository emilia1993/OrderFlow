package com.github.orderflow.entity;

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

    // Many-to-one association with TestataOrdine
    @ManyToOne
    @JoinColumn(name = "id_ordine", nullable = false)
    private TestataOrdine ordine;

    @Column(name = "cod_prodotto")
    private Integer codProdotto;

    private BigDecimal prezzo;
}

