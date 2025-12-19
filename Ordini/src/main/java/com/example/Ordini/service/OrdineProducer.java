package com.example.Ordini.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.Ordini.model.TestataOrdine;


@Service
public class OrdineProducer {

    private final KafkaTemplate<String, TestataOrdine> kafkaTemplate;

    public OrdineProducer(KafkaTemplate<String, TestataOrdine> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void inviaOrdine(TestataOrdine ordine) {
        kafkaTemplate.send("imieiordini1", ordine);
        System.out.println("Ordine inviato: " + ordine.getId());
    }
}
