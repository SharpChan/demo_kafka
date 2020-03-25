package com.example.demo.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
public class KafkaController {
    private static final Logger logger = LogManager.getLogger(KafkaController.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/kafka")
    public String testKafka() {

            kafkaTemplate.send("test","key" , "data:" +"kafka测试" );
        return "success";
    }

    @KafkaListener(topics = "test")
    public void receive(ConsumerRecord<?, ?> consumer) {
        logger.info("{} - {}:{}", consumer.topic(), consumer.key(), consumer.value());
    }
}
