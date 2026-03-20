package com.github.orderflow.controller;

import com.github.orderflow.enums.StatoOrdine;
import com.github.orderflow.model.TestataOrdineDTO;
import com.github.orderflow.service.TestataOrdineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Management", description = "Order management APIs")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ordini")
public class TestataOrdineController {

    private static final Logger logger = LoggerFactory.getLogger(TestataOrdineController.class);

    private final TestataOrdineService ordineService;

    public TestataOrdineController(TestataOrdineService ordineService) {
        this.ordineService = ordineService;
    }

    // GET /ordini → Returns a list of DTOs
    @GetMapping
    public List<TestataOrdineDTO> visualizzaOrdini() {
        logger.info("Request: retrieve all orders");
        return ordineService.getAllOrdiniDTO();
    }

    // POST /ordini → Create a new order with validation
    @PostMapping
    public TestataOrdineDTO creaOrdine(@Valid @RequestBody TestataOrdineDTO ordine) {
        logger.info("Request: create a new order");
        return ordineService.creaOrdineDTO(ordine);
    }

    // PUT /ordini/{id} → Update the entire order
    @PutMapping("/{id}")
    public TestataOrdineDTO aggiornaOrdine(@PathVariable int id, @Valid @RequestBody TestataOrdineDTO ordine) {
        logger.info("Request: update order id {}", id);
        return ordineService.aggiornaOrdineDTO(id, ordine);
    }

    // PATCH /ordini/{id}/stato → Update only the status
    @PatchMapping("/{id}/stato")
    public TestataOrdineDTO aggiornaStatoOrdine(@PathVariable int id, @RequestBody StatoOrdine nuovoStato) {
        logger.info("Request: update order status for id {}", id);
        return ordineService.aggiornaStatoDTO(id, nuovoStato);
    }

    // DELETE /ordini/{id} with Optimistic Lock
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaOrdine(@PathVariable int id) {
        logger.info("Request: delete order id {}", id);
        ordineService.cancellaOrdine(id);
    }
}