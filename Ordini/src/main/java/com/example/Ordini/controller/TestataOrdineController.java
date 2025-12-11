package com.example.Ordini.controller;

import com.example.Ordini.model.TestataOrdine;
import com.example.Ordini.repository.TestataOrdineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        //logger.info("Conversione completata. Numero di ordini convertiti: {}", ordiniModel.size());
        return ordiniModel;
    }
    //Metodo conversione da entity a model
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

        // Convert Model → Entity
        com.example.Ordini.entity.TestataOrdine ordineEntity =
                getConverterFromModelToEntity(ordineModel);

        // Salva nel DB
        com.example.Ordini.entity.TestataOrdine entitySalvata =
                testataOrdineRepository.save(ordineEntity);

        // Riconverti Entity → Model per il ritorno
        TestataOrdine ordineCreato = new TestataOrdine();
        ordineCreato.setId(entitySalvata.getId());
        ordineCreato.setDescrizione(entitySalvata.getDescrizione());
        ordineCreato.setDataConsegna(entitySalvata.getDataConsegna());
        logger.info("E' stato aggiunto un nuovo ordine");
        return ordineCreato;
    }

    // Funzione per la conversione da Model a Entity
    private static com.example.Ordini.entity.TestataOrdine getConverterFromModelToEntity(TestataOrdine ordineModel) {
        // Chiama il metodo che effettivamente converte il Model in Entity
        return converterFromModelToEntity(ordineModel);
    }

    // Conversione effettiva da Model a Entity
    private static com.example.Ordini.entity.TestataOrdine converterFromModelToEntity(TestataOrdine ordineModel) {
        // Creiamo una nuova Entity e popoliamo i campi dalla Model
        com.example.Ordini.entity.TestataOrdine ordineEntity = new com.example.Ordini.entity.TestataOrdine();
        ordineEntity.setDescrizione(ordineModel.getDescrizione());
        ordineEntity.setDataConsegna(ordineModel.getDataConsegna());
        ordineEntity.setStatoOrdine(ordineModel.getStatoOrdine());
        // Se ci sono altre proprietà, aggiungile qui

        return ordineEntity;
    }

    // Cancella un ordine per id (DELETE)

    @DeleteMapping("/{id}")
    public String cancellaOrdine(@PathVariable int id) {
        if (testataOrdineRepository.existsById(id)) {
            testataOrdineRepository.deleteById(id);
            logger.info("l'ordine con id {} e' stato cancellato correttamente", id);
            return "Ordine con id " + id + " cancellato correttamente.";

        } else {
            logger.warn("si e' provato a cancellare l'ordine con id {} ma non esiste",id);
            return "Ordine con id " + id + " non trovato.";
        }
    }
}
