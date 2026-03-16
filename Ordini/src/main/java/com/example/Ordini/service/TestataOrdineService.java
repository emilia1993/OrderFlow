package com.example.Ordini.service;

import com.example.Ordini.entity.TestataOrdine;
import com.example.Ordini.enumModel.StatoOrdine;
import com.example.Ordini.model.TestataOrdineDTO;
import com.example.Ordini.repository.TestataOrdineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestataOrdineService {

    private static final Logger logger = LoggerFactory.getLogger(TestataOrdineService.class);

    private final TestataOrdineRepository repository;

    public TestataOrdineService(TestataOrdineRepository repository) {
        this.repository = repository;
    }

    // ================= GET ALL DTO =================
    public List<TestataOrdineDTO> getAllOrdiniDTO() {
        List<TestataOrdine> ordini = repository.findAll();
        logger.info("Recuperati {} ordini dal database", ordini.size());
        return ordini.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ================= CREATE DTO =================
    public TestataOrdineDTO creaOrdineDTO(TestataOrdineDTO dto) {
        TestataOrdine entity = toEntity(dto);
        TestataOrdine saved = repository.save(entity);
        logger.info("Creato nuovo ordine con id {}", saved.getId());
        return toDTO(saved);
    }

    // ================= UPDATE DTO =================
    @Transactional
    public TestataOrdineDTO aggiornaOrdineDTO(int id, TestataOrdineDTO dto) {
        TestataOrdine ordine = repository.findById(id).orElse(null);

        if (ordine == null) {
            logger.warn("Tentativo di aggiornare ordine inesistente con id {}", id);
            return null;
        }

        ordine.setDescrizione(dto.getDescrizione());
        ordine.setDataConsegna(dto.getDataConsegna());
        ordine.setStatoOrdine(dto.getStatoOrdine());

        TestataOrdine updated = repository.save(ordine);
        logger.info("Ordine con id {} aggiornato correttamente", id);
        return toDTO(updated);
    }

    // ================= PATCH (aggiorna solo lo stato) =================
    @Transactional
    public TestataOrdineDTO aggiornaStatoDTO(int id, StatoOrdine nuovoStato) {
        TestataOrdine ordine = repository.findById(id).orElse(null);

        if (ordine == null) {
            logger.warn("Tentativo di aggiornare stato ordine inesistente con id {}", id);
            return null;
        }

        ordine.setStatoOrdine(nuovoStato);
        TestataOrdine updated = repository.save(ordine);
        logger.info("Stato ordine {} aggiornato a {}", id, nuovoStato);
        return toDTO(updated);
    }

    // ================= DELETE =================
    @Transactional
    public void cancellaOrdine(int id) {
        try {
            TestataOrdine ordine = repository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Tentativo di cancellare ordine inesistente con id {}", id);
                        return new IllegalArgumentException("Ordine con id " + id + " non trovato.");
                    });

            repository.delete(ordine);
            logger.info("Ordine con id {} cancellato correttamente", id);

        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Errore di lock ottimistico sull'ordine {}: {}", id, e.getMessage());
            throw new IllegalStateException("Un altro utente ha già modificato o cancellato questo ordine.");
        }
    }

    // ================= CONVERSIONI ENTITY <-> DTO =================
    private TestataOrdineDTO toDTO(TestataOrdine entity) {
        TestataOrdineDTO dto = new TestataOrdineDTO();
        dto.setId(entity.getId());
        dto.setDescrizione(entity.getDescrizione());
        dto.setDataConsegna(entity.getDataConsegna());
        dto.setStatoOrdine(entity.getStatoOrdine());
        return dto;
    }

    private TestataOrdine toEntity(TestataOrdineDTO dto) {
        TestataOrdine entity = new TestataOrdine();
        entity.setId(dto.getId());
        entity.setDescrizione(dto.getDescrizione());
        entity.setDataConsegna(dto.getDataConsegna());
        entity.setStatoOrdine(dto.getStatoOrdine());
        return entity;
    }
}