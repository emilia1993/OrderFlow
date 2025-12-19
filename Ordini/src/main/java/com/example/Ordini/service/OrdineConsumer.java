package com.example.Ordini.service;

import com.example.Ordini.repository.TestataOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.Ordini.model.TestataOrdine;

@Service
public class OrdineConsumer {

    @Autowired
    private TestataOrdineRepository testataOrdineRepository;

    @KafkaListener(topics = "imieiordini1", groupId = "ordini-group")
    public void riceviOrdine(TestataOrdine ordine) {
        System.out.println("Ordine ricevuto: " + ordine.getId());


        com.example.Ordini.entity.TestataOrdine ordineEntity =
                getConverterFromModelToEntity(ordine);

        com.example.Ordini.entity.TestataOrdine entitySalvata =
                testataOrdineRepository.save(ordineEntity);

        TestataOrdine ordineCreato = new TestataOrdine();
        ordineCreato.setId(entitySalvata.getId());
        ordineCreato.setDescrizione(entitySalvata.getDescrizione());
        ordineCreato.setDataConsegna(entitySalvata.getDataConsegna());

        //logger.info("E' stato aggiunto un nuovo ordine");
        return;
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
}

