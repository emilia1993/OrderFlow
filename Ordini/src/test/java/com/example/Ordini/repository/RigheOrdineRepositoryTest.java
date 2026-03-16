package com.example.Ordini.repository;

import com.example.Ordini.entity.RigheOrdine;
import com.example.Ordini.entity.TestataOrdine;
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
        // Creazione ordine di prova
        TestataOrdine ordine = new TestataOrdine();
        entityManager.persist(ordine);

        // Creazione righe collegate all'ordine
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

        // Recupero dal repository
        List<RigheOrdine> result = righeOrdineRepository.findByOrdineId(ordine.getId());

        // Verifica
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).getCodProdotto());
        assertEquals(102, result.get(1).getCodProdotto());
    }

    @Test
    void testFindByOrdineIdOrdineSenzaRighe() {
        // Ordine senza righe
        TestataOrdine ordine = new TestataOrdine();
        entityManager.persist(ordine);
        entityManager.flush();

        List<RigheOrdine> result = righeOrdineRepository.findByOrdineId(ordine.getId());
        assertEquals(0, result.size());
    }

    @Test
    void testFindByOrdineIdRigheOrdiniDiversi() {
        // Due ordini
        TestataOrdine ordine1 = new TestataOrdine();
        TestataOrdine ordine2 = new TestataOrdine();
        entityManager.persist(ordine1);
        entityManager.persist(ordine2);

        // Righe collegate a ordine1
        RigheOrdine r1 = new RigheOrdine();
        r1.setOrdine(ordine1);
        r1.setCodProdotto(201);
        entityManager.persist(r1);

        // Righe collegate a ordine2
        RigheOrdine r2 = new RigheOrdine();
        r2.setOrdine(ordine2);
        r2.setCodProdotto(202);
        entityManager.persist(r2);

        entityManager.flush();

        // Verifica che ordine1 ritorni solo la sua riga
        List<RigheOrdine> result1 = righeOrdineRepository.findByOrdineId(ordine1.getId());
        assertEquals(1, result1.size());
        assertEquals(201, result1.get(0).getCodProdotto());

        // Verifica che ordine2 ritorni solo la sua riga
        List<RigheOrdine> result2 = righeOrdineRepository.findByOrdineId(ordine2.getId());
        assertEquals(1, result2.size());
        assertEquals(202, result2.get(0).getCodProdotto());
    }
}
