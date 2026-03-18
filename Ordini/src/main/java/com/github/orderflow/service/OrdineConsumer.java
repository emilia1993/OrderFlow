package com.github.orderflow.service;

import com.github.orderflow.entity.TestataOrdine;
import com.github.orderflow.repository.TestataOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.github.orderflow.model.TestataOrdineDTO;

@Service
public class OrdineConsumer {

    @Autowired
    private TestataOrdineRepository testataOrdineRepository;

    @KafkaListener(topics = "imieiordini1", groupId = "ordini-group")
    public void riceviOrdine(TestataOrdineDTO ordine) {
        System.out.println("Order received: " + ordine.getId());

        TestataOrdine ordineEntity =
                getConverterFromModelToEntity(ordine);

        TestataOrdine entitySalvata =
                testataOrdineRepository.save(ordineEntity);

        TestataOrdineDTO ordineCreato = new TestataOrdineDTO();
        ordineCreato.setId(entitySalvata.getId());
        ordineCreato.setDescrizione(entitySalvata.getDescrizione());
        ordineCreato.setDataConsegna(entitySalvata.getDataConsegna());

        //logger.info("E' stato aggiunto un nuovo ordine");
        return;
    }

    private static TestataOrdine getConverterFromModelToEntity(TestataOrdineDTO ordineModel) {
        return converterFromModelToEntity(ordineModel);
    }

    private static TestataOrdine converterFromModelToEntity(TestataOrdineDTO ordineModel) {
        TestataOrdine ordineEntity = new TestataOrdine();
        ordineEntity.setDescrizione(ordineModel.getDescrizione());
        ordineEntity.setDataConsegna(ordineModel.getDataConsegna());
        ordineEntity.setStatoOrdine(ordineModel.getStatoOrdine());
        return ordineEntity;
    }
}

