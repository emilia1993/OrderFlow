package com.github.orderflow.repository;

import com.github.orderflow.entity.TestataOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface TestataOrdineRepository extends JpaRepository<TestataOrdine, Integer> {
    }
