package com.example.Ordini.repository;

import com.example.Ordini.entity.RigheOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RigheOrdineRepository extends JpaRepository<RigheOrdine, Integer> {

    // Trova tutte le righe associate a un ordine
    List<RigheOrdine> findByOrdineId(int id);
}
