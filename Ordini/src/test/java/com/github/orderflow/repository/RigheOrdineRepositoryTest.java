package com.github.orderflow.repository;

import com.github.orderflow.entity.RigheOrdine;
import com.github.orderflow.entity.TestataOrdine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RigheOrdineRepositoryTest {

    @Autowired
    private RigheOrdineRepository righeOrdineRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByOrdineId() {
        // Test order creation
        TestataOrdine ordine = new TestataOrdine();
        entityManager.persist(ordine);

        // Creation of lines associated with the order
        RigheOrdine r1 = new RigheOrdine();
        r1.setOrdine(ordine);
        r1.setCodProdotto(101);
        r1.setPrezzo(new BigDecimal("10.50"));
        entityManager.persist(r1);

        RigheOrdine r2 = new RigheOrdine();
        r2.setOrdine(ordine);
        r2.setCodProdotto(102);
        r2.setPrezzo(new BigDecimal("20.75"));
        entityManager.persist(r2);

        entityManager.flush();

        // Retrieve from repository
        List<RigheOrdine> result = righeOrdineRepository.findByOrdineId(ordine.getId());

        // Verify
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).getCodProdotto());
        assertEquals(102, result.get(1).getCodProdotto());
    }

    @Test
    void testFindByOrdineIdOrdineSenzaRighe() {
        // Order without order lines
        TestataOrdine ordine = new TestataOrdine();
        entityManager.persist(ordine);
        entityManager.flush();

        List<RigheOrdine> result = righeOrdineRepository.findByOrdineId(ordine.getId());
        assertEquals(0, result.size());
    }

    @Test
    void testFindByOrdineIdRigheOrdiniDiversi() {
        // Two orders
        TestataOrdine ordine1 = new TestataOrdine();
        TestataOrdine ordine2 = new TestataOrdine();
        entityManager.persist(ordine1);
        entityManager.persist(ordine2);

        // Lines associated with order1
        RigheOrdine r1 = new RigheOrdine();
        r1.setOrdine(ordine1);
        r1.setCodProdotto(201);
        entityManager.persist(r1);

        // Lines associated with order2
        RigheOrdine r2 = new RigheOrdine();
        r2.setOrdine(ordine2);
        r2.setCodProdotto(202);
        entityManager.persist(r2);

        entityManager.flush();

        // Verify that order1 returns only its line
        List<RigheOrdine> result1 = righeOrdineRepository.findByOrdineId(ordine1.getId());
        assertEquals(1, result1.size());
        assertEquals(201, result1.get(0).getCodProdotto());

        // Verify that order2 returns only its line
        List<RigheOrdine> result2 = righeOrdineRepository.findByOrdineId(ordine2.getId());
        assertEquals(1, result2.size());
        assertEquals(202, result2.get(0).getCodProdotto());
    }
}
