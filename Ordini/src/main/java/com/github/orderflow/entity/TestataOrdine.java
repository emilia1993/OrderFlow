package com.github.orderflow.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.github.orderflow.enumModel.StatoOrdine;

@Getter
@Setter
@Entity
@Table(name = "testata_ordine")
public class TestataOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descrizione;

    @Column(name = "data_consegna")
    private LocalDate dataConsegna;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_ordine")
    private StatoOrdine statoOrdine;

    // Optimistic locking field
    @Version
    private Integer version;

    // One-to-many relationship with RigheOrdine
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RigheOrdine> righe;

    //One-to-many relationship with Pagamenti
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamenti> pagamenti;
}
