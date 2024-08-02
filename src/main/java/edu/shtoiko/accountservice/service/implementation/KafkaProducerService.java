package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.service.MessageProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService implements MessageProducerService {
    private static final String TOPIC = "transactions";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
