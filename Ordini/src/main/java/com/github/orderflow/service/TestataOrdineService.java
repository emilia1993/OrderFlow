package com.github.orderflow.service;

import com.github.orderflow.entity.TestataOrdine;
import com.github.orderflow.enums.StatoOrdine;
import com.github.orderflow.model.TestataOrdineDTO;
import com.github.orderflow.repository.TestataOrdineRepository;
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

    // GET ALL DTO
    public List<TestataOrdineDTO> getAllOrdiniDTO() {
        List<TestataOrdine> ordini = repository.findAll();
        logger.info("Retrieved {} orders from the database", ordini.size());
        return ordini.stream()
                .map(this::toDTO)
                .toList();
    }

    // CREATE DTO
    public TestataOrdineDTO creaOrdineDTO(TestataOrdineDTO dto) {
        TestataOrdine entity = toEntity(dto);
        TestataOrdine saved = repository.save(entity);
        logger.info("New order created with id {}", saved.getId());
        return toDTO(saved);
    }

    // UPDATE DTO
    @Transactional
    public TestataOrdineDTO aggiornaOrdineDTO(int id, TestataOrdineDTO dto) {
        TestataOrdine ordine = repository.findById(id).orElse(null);

        if (ordine == null) {
            logger.warn("Trying to update order that does not exist with id {}", id);
            return null;
        }

        ordine.setDescrizione(dto.getDescrizione());
        ordine.setDataConsegna(dto.getDataConsegna());
        ordine.setStatoOrdine(dto.getStatoOrdine());

        TestataOrdine updated = repository.save(ordine);
        logger.info("Order with id {} updated successfully", id);
        return toDTO(updated);
    }

    // PATCH (update only the status)
    @Transactional
    public TestataOrdineDTO aggiornaStatoDTO(int id, StatoOrdine nuovoStato) {
        TestataOrdine ordine = repository.findById(id).orElse(null);

        if (ordine == null) {
            logger.warn("Trying to update order status for non-existent id {}", id);
            return null;
        }

        ordine.setStatoOrdine(nuovoStato);
        TestataOrdine updated = repository.save(ordine);
        logger.info("Order status {} updated to {}", id, nuovoStato);
        return toDTO(updated);
    }

    // DELETE
    @Transactional
    public void cancellaOrdine(int id) {
        try {
            TestataOrdine ordine = repository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Trying to delete order that does not exist with id {}", id);
                        return new IllegalArgumentException("Order with id " + id + " not found.");
                    });

            repository.delete(ordine);
            logger.info("Order with id {} deleted successfully", id);

        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Optimistic lock error on order {}: {}", id, e.getMessage());
            throw new IllegalStateException("This order has already been modified or deleted by another user.");
        }
    }

    // ENTITY <-> DTO CONVERSIONS
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