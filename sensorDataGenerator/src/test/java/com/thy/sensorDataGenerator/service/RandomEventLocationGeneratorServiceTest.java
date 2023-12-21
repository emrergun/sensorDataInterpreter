package com.thy.sensorDataGenerator.service;

import com.thy.sensorDataGenerator.model.EventLocation;
import com.thy.sensorDataGenerator.model.Geometry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomEventLocationGeneratorServiceTest {

    @Test
    void getRandomEventLocationTest() {
        EventLocation randomEventLocation = RandomEventLocationGeneratorService.getRandomEventLocation();
        assertNotNull(randomEventLocation, "EventLocation object should not be null.");
        assertNotNull(randomEventLocation.getGeometry(), "Geometry object should not be null.");
    }

    @Test
    void getRandomGeometryTest() {
        Geometry randomGeometry = RandomEventLocationGeneratorService.getRandomGeometry();
        assertNotNull(randomGeometry, "Geometry object should not be null.");
        assertEquals(2, randomGeometry.getCoordinates().size(), "Coordinate list should contain 2 coordinate data");
    }
}