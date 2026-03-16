package com.example.Ordini.controller;

import com.example.Ordini.enumModel.StatoOrdine;
import com.example.Ordini.model.TestataOrdineDTO; // DTO invece di entity
import com.example.Ordini.service.TestataOrdineService;
import jakarta.validation.Valid; // <- per validazione DTO
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ordini")
public class TestataOrdineController {

    private static final Logger logger = LoggerFactory.getLogger(TestataOrdineController.class);

    private final TestataOrdineService ordineService;

    public TestataOrdineController(TestataOrdineService ordineService) {
        this.ordineService = ordineService;
    }

    // GET /ordini → restituisce lista DTO
    @GetMapping
    public List<TestataOrdineDTO> visualizzaOrdini() {
        logger.info("Richiesta: visualizzazione di tutti gli ordini");
        return ordineService.getAllOrdiniDTO(); // <-- ora restituisce DTO
    }

    // POST /ordini → crea nuovo ordine con validazione
    @PostMapping
    public TestataOrdineDTO creaOrdine(@Valid @RequestBody TestataOrdineDTO ordine) {
        logger.info("Richiesta: creazione nuovo ordine");
        return ordineService.creaOrdineDTO(ordine); // <-- servizio con DTO
    }

    // PUT /ordini/{id} → aggiorna tutto l'ordine
    @PutMapping("/{id}")
    public TestataOrdineDTO aggiornaOrdine(@PathVariable int id, @Valid @RequestBody TestataOrdineDTO ordine) {
        logger.info("Richiesta: aggiornamento ordine id {}", id);
        return ordineService.aggiornaOrdineDTO(id, ordine);
    }

    // PATCH /ordini/{id}/stato → aggiorna solo lo stato
    @PatchMapping("/{id}/stato")
    public TestataOrdineDTO aggiornaStatoOrdine(@PathVariable int id, @RequestBody StatoOrdine nuovoStato) {
        logger.info("Richiesta: aggiornamento stato ordine id {}", id);
        return ordineService.aggiornaStatoDTO(id, nuovoStato);
    }

    // DELETE /ordini/{id} con Optimistic Lock
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // REST: 204 se successo
    public void cancellaOrdine(@PathVariable int id) {
        logger.info("Richiesta: cancellazione ordine id {}", id);
        ordineService.cancellaOrdine(id); // eventuali eccezioni propagate
    }
}