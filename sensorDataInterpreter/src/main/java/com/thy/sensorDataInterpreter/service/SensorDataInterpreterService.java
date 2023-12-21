package com.thy.sensorDataInterpreter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thy.sensorDataInterpreter.model.Sensor;
import com.thy.sensorDataInterpreter.model.StatusChange;
import com.thy.sensorDataInterpreter.persistence.entity.FormulaEntity;
import com.thy.sensorDataInterpreter.persistence.entity.OperationalSensorEntity;
import com.thy.sensorDataInterpreter.persistence.entity.StatisticalSensorEntity;
import com.thy.sensorDataInterpreter.repository.jpa.OperationalSensorRepository;
import com.thy.sensorDataInterpreter.repository.jpa.StatisticalSensorRepository;
import com.thy.sensorDataInterpreter.repository.mongo.MathFormulaRepository;
import com.thy.sensorDataInterpreter.repository.mongo.SensorRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class SensorDataInterpreterService {

    private final ObjectMapper objectMapper;
    private final SensorRepository sensorRepository;
    private final MathFormulaRepository mathFormulaRepository;
    private final OperationalSensorRepository operationalSensorRepository;
    private final StatisticalSensorRepository statisticalSensorRepository;

    private final Logger logger = LoggerFactory.getLogger(SensorDataInterpreterService.class);

    @Value("${executor-service.fixed-thread-pool-size:50}")
    private int fixedThreadPoolSize;

    @Value("${default-power-value:2}")
    private int defaultPowerValue;

    private long consumedMessageCount = 0;

    private ExecutorService executorService;

    private final CopyOnWriteArrayList<List<OperationalSensorEntity>> listOfOperationalDataList = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<List<StatisticalSensorEntity>> listOfStatisticalDataList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<OperationalSensorEntity> currentOperationalDataList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<StatisticalSensorEntity> currentStatisticalDataList = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(fixedThreadPoolSize);
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.group-id}", concurrency = "${spring.kafka.concurrency-number}")
    public void listen(String message) throws JsonProcessingException {

        Sensor sensor = objectMapper.readValue(message, Sensor.class);

        executorService.submit(() -> addOperationalDataToList(sensor));
        executorService.submit(() -> addStatisticalDataToList(sensor));

        System.out.println("consumedMessageCount: " + ++consumedMessageCount);
    }

    private void addOperationalDataToList(Sensor sensor) {
        try {
            OperationalSensorEntity entity = OperationalSensorEntity.builder()
                    .id(sensor.getId())
                    .type(sensor.getType())
                    .temperature(sensor.getTemperature())
                    .airPressure(sensor.getAirPressure())
                    .humidity(sensor.getHumidity())
                    .batteryCharge(sensor.getBatteryCharge())
                    .batteryVoltage(sensor.getBatteryVoltage())
                    .calculatedResult(getFormula(sensor.getHumidity()))
                    .build();

            if (currentOperationalDataList.size() > 500) {
                listOfOperationalDataList.add(currentOperationalDataList);
                currentOperationalDataList = new CopyOnWriteArrayList<>();
            }
            currentOperationalDataList.add(entity);

        } catch (Exception e) {
            writeSensorDataToFile(sensor.toString(), sensor.getId());
            sensorRepository.save(sensor);
        }
    }

    private void addStatisticalDataToList(Sensor sensor) {
        try {
            for (StatusChange statusChange : sensor.getStatusChangeList()) {
                StatisticalSensorEntity entity = StatisticalSensorEntity.builder()
                        .deviceId(statusChange.getDeviceId())
                        .vehicleId(statusChange.getVehicleId())
                        .vehicleType(statusChange.getVehicleType())
                        .eventType(statusChange.getEventType())
                        .eventTime(statusChange.getEventTime())
                        .geometryType(statusChange.getEventLocation().getGeometry().getType())
                        .latitude(statusChange.getEventLocation().getGeometry().getCoordinates().get(0))
                        .longitude(statusChange.getEventLocation().getGeometry().getCoordinates().get(1))
                        .build();

                if (currentStatisticalDataList.size() > 500) {
                    listOfStatisticalDataList.add(currentStatisticalDataList);
                    currentStatisticalDataList = new CopyOnWriteArrayList<>();
                }
                currentStatisticalDataList.add(entity);

            }
        } catch (Exception e) {
            writeSensorDataToFile(sensor.toString(), sensor.getId());
            sensorRepository.save(sensor);
        }
    }

    @Scheduled(fixedDelayString = "${scheduled.database-saver-in-ms:1000}")
    public void scheduledDatabaseSaver() {
        for (int i = 0; i < listOfStatisticalDataList.size(); i++) {
            executorService.submit(this::saveStatisticalDataToDB);
        }
        for (int i = 0; i < listOfOperationalDataList.size(); i++) {
            executorService.submit(this::saveOperationalDataToDB);
        }

    }

    private void saveStatisticalDataToDB() {
        if (!listOfStatisticalDataList.isEmpty()) {
            List<StatisticalSensorEntity> statisticalSensorEntities = listOfStatisticalDataList.remove(0);
            statisticalSensorRepository.saveAll(statisticalSensorEntities);
        }
    }

    private void saveOperationalDataToDB() {
        if (!listOfOperationalDataList.isEmpty()) {
            List<OperationalSensorEntity> operationalSensorEntities = listOfOperationalDataList.remove(0);
            operationalSensorRepository.saveAll(operationalSensorEntities);
        }
    }

    private double getFormula(double value) {
        List<Optional<FormulaEntity>> power = mathFormulaRepository.findFormula(value);
        return power.get(0).map(formulaEntity ->
                        Math.pow(value, formulaEntity.getPower())).
                orElseGet(() -> Math.pow(value, defaultPowerValue));
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
