package com.thy.sensorDataGenerator.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventLocation {
    private Geometry geometry;
}