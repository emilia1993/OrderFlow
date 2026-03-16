package com.example.Ordini.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.Ordini.model.TestataOrdineDTO;

@Service
public class OrdineProducer {

    private final KafkaTemplate<String, TestataOrdineDTO> kafkaTemplate;

    public OrdineProducer(KafkaTemplate<String, TestataOrdineDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void inviaOrdine(TestataOrdineDTO ordine) {
        kafkaTemplate.send("imieiordini1", ordine);
        System.out.println("Ordine inviato: " + ordine.getId());
    }
}
