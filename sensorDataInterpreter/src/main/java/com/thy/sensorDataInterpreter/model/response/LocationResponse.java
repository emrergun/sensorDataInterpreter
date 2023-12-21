package com.thy.sensorDataInterpreter.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResponse {

    private String deviceId;

    private Long eventTime;

    private String geometryType;

    private double latitude;

    private double longitude;
}
