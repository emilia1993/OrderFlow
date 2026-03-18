package com.github.orderflow.repository;

import com.github.orderflow.entity.Pagamenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentiRepository extends JpaRepository<Pagamenti, Integer> {

    // Find all payments associated with an order
    List<Pagamenti> findByOrdineId(int id);
}
