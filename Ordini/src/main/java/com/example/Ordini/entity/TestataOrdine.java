package com.example.Ordini.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.example.Ordini.enumModel.StatoOrdine;

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

    // Campo per LOCK OTTIMISTICO
    @Version
    private Integer version;

    // Relazione con righe d'ordine
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RigheOrdine> righe;

    // Relazione con pagamenti
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamenti> pagamenti;
}
