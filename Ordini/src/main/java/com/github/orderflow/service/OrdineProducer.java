package com.github.orderflow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.github.orderflow.model.TestataOrdineDTO;

@Service
public class OrdineProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrdineProducer.class);

    private final KafkaTemplate<String, TestataOrdineDTO> kafkaTemplate;

    public OrdineProducer(KafkaTemplate<String, TestataOrdineDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void inviaOrdine(TestataOrdineDTO ordine) {
        kafkaTemplate.send("imieiordini1", ordine);
        logger.info("Order sent: {}", ordine.getId());
    }
}