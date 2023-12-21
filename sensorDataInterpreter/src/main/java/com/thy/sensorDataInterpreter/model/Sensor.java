package com.thy.sensorDataInterpreter.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@ToString
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Sensor")
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