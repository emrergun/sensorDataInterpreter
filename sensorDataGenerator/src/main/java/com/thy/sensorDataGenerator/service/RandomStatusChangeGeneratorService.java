package com.thy.sensorDataGenerator.service;

import com.thy.sensorDataGenerator.model.PropulsionType;
import com.thy.sensorDataGenerator.model.StatusChange;
import com.thy.sensorDataGenerator.util.RandomGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RandomStatusChangeGeneratorService {

    public StatusChange getRandomStatusChangeData() {
        return StatusChange.builder()
                .deviceId(UUID.randomUUID().toString())
                .vehicleId(generateRandomVehicleId())
                .vehicleType(generateRandomVehicleType())
                .propulsionTypeList(getRandomPropulsionTypeList())
                .eventType(generateRandomEventType())
                .eventTypeReason(generateRandomEventTypeReason())
                .eventTime(RandomGeneratorUtil.getRandomTimeStamp())
                .eventLocation(RandomEventLocationGeneratorService.getRandomEventLocation())
                .build();
    }

    private String generateRandomVehicleId() {
        int length = 10;
        boolean useLetters = false;
        boolean useNumbers = true;
        return RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
    }

    private String generateRandomVehicleType() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
    }

    private List<PropulsionType> getRandomPropulsionTypeList() {
        List<PropulsionType> propulsionTypeList = new ArrayList<>();
        propulsionTypeList.add(RandomGeneratorUtil.getRandomPropulsionType());
        return propulsionTypeList;
    }

    private String generateRandomEventType() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
    }

    private String generateRandomEventTypeReason() {
        int length = 50;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
    }
}
