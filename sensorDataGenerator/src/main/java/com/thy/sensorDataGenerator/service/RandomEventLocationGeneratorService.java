package com.thy.sensorDataGenerator.service;

import com.thy.sensorDataGenerator.model.EventLocation;
import com.thy.sensorDataGenerator.model.Geometry;
import com.thy.sensorDataGenerator.util.RandomGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RandomEventLocationGeneratorService {

    public static EventLocation getRandomEventLocation() {
        return EventLocation.builder()
                .geometry(getRandomGeometry())
                .build();
    }

    public static Geometry getRandomGeometry() {
        return Geometry.builder()
                .type("Point")
                .coordinates(getRandomCoordinates())
                .build();
    }

    private static List<Double> getRandomCoordinates() {
        List<Double> coordinates = new ArrayList<>();
        double max = 180.00;
        double min = 90.00;
        // generate random double value between -90 and +90
        coordinates.add(RandomGeneratorUtil.getRandomDouble(max) - min);
        coordinates.add(RandomGeneratorUtil.getRandomDouble(max) - min);

        return coordinates;
    }

}
