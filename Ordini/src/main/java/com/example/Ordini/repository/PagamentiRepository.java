package com.example.Ordini.repository;

import com.example.Ordini.entity.Pagamenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentiRepository extends JpaRepository<Pagamenti, Integer> {

    // Trova tutti i pagamenti associati a un ordine
    List<Pagamenti> findByOrdineId(int id);
}
