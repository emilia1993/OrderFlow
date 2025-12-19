package com.example.Ordini.controller;

import com.example.Ordini.model.TestataOrdine;
import com.example.Ordini.repository.TestataOrdineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ordini")
public class TestataOrdineController {

    private static final Logger logger = LoggerFactory.getLogger(TestataOrdineController.class);

    @Autowired
    private TestataOrdineRepository testataOrdineRepository;

    // Visualizza tutti gli ordini (GET)
    @GetMapping
    public List<TestataOrdine> visualizzaOrdini() {

        logger.info("Richiesta: visualizzazione di tutti gli ordini");

        List<com.example.Ordini.entity.TestataOrdine> ordini = testataOrdineRepository.findAll();
        logger.info("Numero di ordini trovati nel database: {}", ordini.size());
        List<TestataOrdine> ordiniModel = new ArrayList<>();

        converterFromEntityToModel(ordini, ordiniModel);
        return ordiniModel;
    }

    private static void converterFromEntityToModel(List<com.example.Ordini.entity.TestataOrdine> ordini, List<TestataOrdine> ordiniModel) {
        for (com.example.Ordini.entity.TestataOrdine ordine : ordini) {
            TestataOrdine ordineModel = new TestataOrdine();
            ordineModel.setId(ordine.getId());
            ordineModel.setDescrizione(ordine.getDescrizione());
            ordineModel.setDataConsegna(ordine.getDataConsegna());
            ordineModel.setStatoOrdine(ordine.getStatoOrdine());

            ordiniModel.add(ordineModel);
        }
    }

    @PostMapping
    public TestataOrdine creaOrdine(@RequestBody TestataOrdine ordineModel) {

        com.example.Ordini.entity.TestataOrdine ordineEntity =
                getConverterFromModelToEntity(ordineModel);

        com.example.Ordini.entity.TestataOrdine entitySalvata =
                testataOrdineRepository.save(ordineEntity);

        TestataOrdine ordineCreato = new TestataOrdine();
        ordineCreato.setId(entitySalvata.getId());
        ordineCreato.setDescrizione(entitySalvata.getDescrizione());
        ordineCreato.setDataConsegna(entitySalvata.getDataConsegna());

        logger.info("E' stato aggiunto un nuovo ordine");
        return ordineCreato;
    }

    private static com.example.Ordini.entity.TestataOrdine getConverterFromModelToEntity(TestataOrdine ordineModel) {
        return converterFromModelToEntity(ordineModel);
    }

    private static com.example.Ordini.entity.TestataOrdine converterFromModelToEntity(TestataOrdine ordineModel) {
        com.example.Ordini.entity.TestataOrdine ordineEntity = new com.example.Ordini.entity.TestataOrdine();
        ordineEntity.setDescrizione(ordineModel.getDescrizione());
        ordineEntity.setDataConsegna(ordineModel.getDataConsegna());
        ordineEntity.setStatoOrdine(ordineModel.getStatoOrdine());
        return ordineEntity;
    }

    // DELETE con OPTIMISTIC LOCK
    @Transactional
    @DeleteMapping("/{id}")
    public String cancellaOrdine(@PathVariable int id) {

        try {
            // 1. CARICO L'ENTITÀ (necessario per usare la version)
            com.example.Ordini.entity.TestataOrdine ordine =
                    testataOrdineRepository.findById(id)
                            .orElse(null);

            if (ordine == null) {
                logger.warn("Tentativo di cancellare ordine inesistente: {}", id);
                return "Ordine con id " + id + " non trovato.";
            }

            // 2. CANCELLO l'oggetto completo (include @Version)
            testataOrdineRepository.delete(ordine);

            logger.info("Ordine con id {} cancellato correttamente.", id);
            return "Ordine con id " + id + " cancellato correttamente.";

        } catch (ObjectOptimisticLockingFailureException e) {
            // 3. QUA VIENE PRESO IL CONFLITTO DI LOCK
            logger.error("CONFLITTO DI LOCK su DELETE ordine {}: {}", id, e.getMessage());
            return "Errore: un altro utente ha già modificato o cancellato questo ordine.";
        }
    }
}
