package com.thy.sensorDataGenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.producer.sensor_topic:sensor_topic}")
    private String sensorTopicName;

    public CompletableFuture<SendResult<String, String>> sendRandomGeneratedSensorMessage(String msg) {
        return kafkaTemplate.send(sensorTopicName, msg);
    }
}

