package com.example.Ordini.repository;

import com.example.Ordini.entity.Pagamenti;
import com.example.Ordini.entity.TestataOrdine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PagamentiRepositoryTest {

    @Autowired
    private PagamentiRepository pagamentiRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByOrdineId() {
        // Inserisci ordine e pagamenti di prova
        TestataOrdine ordine = new TestataOrdine();
        entityManager.persist(ordine);

        Pagamenti p1 = new Pagamenti();
        p1.setOrdine(ordine);
        entityManager.persist(p1);

        Pagamenti p2 = new Pagamenti();
        p2.setOrdine(ordine);
        entityManager.persist(p2);

        entityManager.flush();

        List<Pagamenti> result = pagamentiRepository.findByOrdineId(ordine.getId());
        assertEquals(2, result.size());
    }
}