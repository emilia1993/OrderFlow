package com.github.orderflow.repository;

import com.github.orderflow.entity.RigheOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RigheOrdineRepository extends JpaRepository<RigheOrdine, Integer> {

    // Find all lines associated with an order
    List<RigheOrdine> findByOrdineId(int id);
}
