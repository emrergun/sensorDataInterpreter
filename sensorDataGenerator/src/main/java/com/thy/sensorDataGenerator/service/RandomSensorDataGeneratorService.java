package com.thy.sensorDataGenerator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thy.sensorDataGenerator.model.Sensor;
import com.thy.sensorDataGenerator.model.StatusChange;
import com.thy.sensorDataGenerator.util.RandomGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RandomSensorDataGeneratorService {
    private final Logger logger = LoggerFactory.getLogger(RandomSensorDataGeneratorService.class);

    private final RandomStatusChangeGeneratorService statusChangeGenerator;
    private final ProducerService producerService;
    private final ObjectMapper objectMapper;

    @Value("${scheduled.random-generated-sensor-data-max-number:50}")
    private int maxTotalSensorData;

    private long generatedSensorDataCount = 0;
    private long id = 0;


    @Scheduled(fixedDelayString = "${scheduled.random-sensor-data-generator-fixed-delay-in-ms:1000}")
    public void scheduleGenerateRandomSensorData() {
        // Generate random sensor data between [100-5100] count
        int totalSensorData = (RandomGeneratorUtil.getRandomInt(maxTotalSensorData) * 100) + 100;
        generatedSensorDataCount = generatedSensorDataCount + totalSensorData;
        System.out.println("Kafka log send starting...");
        IntStream.range(0, totalSensorData)
                .forEach(index -> {
                    Sensor sensor = this.getRandomSensorData();
                    CompletableFuture<SendResult<String, String>> future;
                    try {
                        future = producerService.sendRandomGeneratedSensorMessage(objectMapper.writeValueAsString(sensor));

                        future.whenComplete((result, ex) -> {
                            if (ex != null) {
                                System.out.println("Unable to send message=[" +
                                        sensor.toString() + "] due to : " + ex.getMessage());
                                writeSensorDataToFile(sensor.toString(), sensor.getId());
                            }
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                });

        System.out.println("\n***************************************************");
        System.out.println("Kafka log sent...");
        System.out.println("Current Time: " + getCurrentTime() + ", randomly generated sensor data in this period: "
                + totalSensorData + ", total generated sensor data: " + generatedSensorDataCount);
        System.out.println("***************************************************\n\n");
    }

    public Sensor getRandomSensorData() {
        return Sensor.builder()
                .id(generateRandomID())
                .type(generateRandomType())
                .temperature(generateRandomTemperature())
                .airPressure(generateRandomAirPressure())
                .humidity(generateRandomHumidity())
                .lightLevel(generateRandomLightLevel())
                .batteryCharge(generateRandomBatteryCharge())
                .batteryVoltage(generateRandomBatteryVoltage())
                .statusChangeList(generateRandomStatusChangeList())
                .build();
    }

    private String generateRandomID() {
        return Long.toString(++id);
    }

    private String generateRandomType() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
    }

    private String generateRandomTemperature() {
        double upperLimit = 100.00;
        return Double.toString(RandomGeneratorUtil.getRandomDouble(upperLimit));
    }

    private String generateRandomAirPressure() {
        double upperLimit = 100000.00;
        return Double.toString(RandomGeneratorUtil.getRandomDouble(upperLimit));
    }

    private double generateRandomHumidity() {
        double upperLimit = 100.00;
        return RandomGeneratorUtil.getRandomDouble(upperLimit);
    }

    private int generateRandomLightLevel() {
        int upperLimit = 1000;
        return RandomGeneratorUtil.getRandomInt(upperLimit);
    }

    private double generateRandomBatteryCharge() {
        double upperLimit = 100.00;
        return RandomGeneratorUtil.getRandomDouble(upperLimit);
    }

    private double generateRandomBatteryVoltage() {
        double upperLimit = 50.00;
        return RandomGeneratorUtil.getRandomDouble(upperLimit);
    }

    private List<StatusChange> generateRandomStatusChangeList() {
        List<StatusChange> statusChangeList = new ArrayList<>();
        statusChangeList.add(statusChangeGenerator.getRandomStatusChangeData());

        return statusChangeList;
    }

    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        return sdf.format(now);
    }

    private void writeSensorDataToFile(String sensor, String id) {
        String fileName = id + "_sensor_data.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(sensor);
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
