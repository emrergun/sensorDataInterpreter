package com.thy.sensorDataGenerator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Geometry {
    private String type;
    private List<Double> coordinates;
}