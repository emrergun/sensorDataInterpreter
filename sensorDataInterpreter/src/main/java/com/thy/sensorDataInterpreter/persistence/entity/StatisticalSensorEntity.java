package com.thy.sensorDataInterpreter.persistence.entity;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Cacheable
@Table(name = "StatisticalSensorEntity")
public class StatisticalSensorEntity {

    @Id
    private String deviceId;

    private String vehicleId;

    private String vehicleType;

    private String eventType;

    private String eventTypeReason;

    private long eventTime;

    private String geometryType;

    private double latitude;

    private double longitude;

}
