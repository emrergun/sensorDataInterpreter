package com.thy.sensorDataInterpreter.model.request;

import lombok.Data;

@Data
public class LocationRequest {

    private String deviceId;

    private Long startTimeStamp;

    private Long finishTimeStamp;
}
