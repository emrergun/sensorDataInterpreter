package com.thy.sensorDataGenerator.model;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Data
public class Sensor {
    private String id;
    private String type;
    private String temperature;
    private String airPressure;
    private double humidity;
    private int lightLevel;
    private double batteryCharge;
    private double batteryVoltage;
    private List<StatusChange> statusChangeList;
}