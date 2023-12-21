package com.thy.sensorDataInterpreter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Cacheable
@Table(name = "OperationalSensorEntity")
public class OperationalSensorEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String type;

    private String temperature;

    private String airPressure;

    private double humidity;

    private int lightLevel;

    private double batteryCharge;

    private double batteryVoltage;

    private double calculatedResult;

}
