package com.thy.sensorDataGenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thy.sensorDataGenerator.model.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RandomSensorDataGeneratorServiceTest {

    private RandomSensorDataGeneratorService service;

    @Mock
    RandomStatusChangeGeneratorService statusChangeGenerator;

    @Mock
    ProducerService producerService;

    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        service = new RandomSensorDataGeneratorService(statusChangeGenerator,producerService,objectMapper);
    }

    @Test
    void scheduleGenerateRandomSensorDataTest() throws ExecutionException, InterruptedException {
        Sensor randomSensorData = service.getRandomSensorData();
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(future.get().getProducerRecord().value()).thenReturn(randomSensorData.toString());
        when(producerService.sendRandomGeneratedSensorMessage("")).thenReturn(future);
        service.scheduleGenerateRandomSensorData();
    }

    @Test
    void getRandomSensorDataTest() {
        Sensor randomSensorData = service.getRandomSensorData();

        assertNotNull(randomSensorData, "Sensor object should not be null.");
        assertFalse(randomSensorData.getStatusChangeList().isEmpty(),
                "Status Change List should contain min 1 data.");
        assertNotNull(randomSensorData.getStatusChangeList().get(0).getEventLocation(),
                "EventLocation object should not be null.");
    }
}