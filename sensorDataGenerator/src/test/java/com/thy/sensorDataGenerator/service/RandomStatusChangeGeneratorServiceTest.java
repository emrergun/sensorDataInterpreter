package com.thy.sensorDataGenerator.service;

import com.thy.sensorDataGenerator.model.StatusChange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomStatusChangeGeneratorServiceTest {

    RandomStatusChangeGeneratorService service = new RandomStatusChangeGeneratorService();

    @Test
    void getRandomStatusChangeDataTest() {
        StatusChange randomStatusChangeData = service.getRandomStatusChangeData();

        assertNotNull(randomStatusChangeData, "StatusChange object should not be null.");
        assertNotNull(randomStatusChangeData.getEventLocation(), "EventLocation object should not be null.");
        assertFalse(randomStatusChangeData.getPropulsionTypeList().isEmpty(),
                "PropulsionTypeList should contain min. 1 data");
    }
}