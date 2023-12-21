package com.thy.sensorDataGenerator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StatusChange {
    private String deviceId;
    private String vehicleId;
    private String vehicleType;
    private List<PropulsionType> propulsionTypeList;
    private String eventType;
    private String eventTypeReason;
    private long eventTime;
    private EventLocation eventLocation;
}