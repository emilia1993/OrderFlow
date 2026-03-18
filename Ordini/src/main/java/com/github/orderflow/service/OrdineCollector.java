package com.github.orderflow.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.github.orderflow.model.TestataOrdineDTO;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrdineCollector {
    private final List<TestataOrdineDTO> buffer = new ArrayList<>();

    @KafkaListener(topics = "imieiordini1", groupId = "ordini")
    public void onMessage(TestataOrdineDTO ordine) {
        synchronized(buffer) {
            buffer.add(ordine);
        }
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void logMessages() {
        synchronized(buffer) {
            buffer.forEach(o -> System.out.println("LOG SCHEDULER: " + o));
            buffer.clear();
        }
    }
}
