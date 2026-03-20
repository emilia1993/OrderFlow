package com.github.orderflow.service;

import com.github.orderflow.entity.TestataOrdine;
import com.github.orderflow.repository.TestataOrdineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.github.orderflow.model.TestataOrdineDTO;

@Service
public class OrdineConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrdineConsumer.class);

    private final TestataOrdineRepository testataOrdineRepository;

    public OrdineConsumer(TestataOrdineRepository testataOrdineRepository) {
        this.testataOrdineRepository = testataOrdineRepository;
    }

    @KafkaListener(topics = "imieiordini1", groupId = "ordini-group")
    public void riceviOrdine(TestataOrdineDTO ordine) {

        logger.info("Order received: {}", ordine.getId());

        TestataOrdine ordineEntity = converterFromModelToEntity(ordine);

        TestataOrdine entitySalvata = testataOrdineRepository.save(ordineEntity);

        logger.info("Order saved with id: {}", entitySalvata.getId());
    }

    private TestataOrdine converterFromModelToEntity(TestataOrdineDTO ordineModel) {
        TestataOrdine ordineEntity = new TestataOrdine();
        ordineEntity.setDescrizione(ordineModel.getDescrizione());
        ordineEntity.setDataConsegna(ordineModel.getDataConsegna());
        ordineEntity.setStatoOrdine(ordineModel.getStatoOrdine());
        return ordineEntity;
    }
}