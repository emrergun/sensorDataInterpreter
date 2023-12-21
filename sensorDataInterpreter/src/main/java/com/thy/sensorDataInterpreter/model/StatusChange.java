package com.thy.sensorDataInterpreter.model;

import lombok.*;

import java.util.List;

@Builder
@ToString
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
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