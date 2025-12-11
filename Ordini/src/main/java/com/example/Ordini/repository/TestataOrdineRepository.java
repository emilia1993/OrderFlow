package com.example.Ordini.repository;

import com.example.Ordini.entity.TestataOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface TestataOrdineRepository extends JpaRepository<TestataOrdine, Integer> {
    }
