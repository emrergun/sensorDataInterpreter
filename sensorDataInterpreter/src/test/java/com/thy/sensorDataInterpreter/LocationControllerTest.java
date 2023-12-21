package com.thy.sensorDataInterpreter;

import com.thy.sensorDataInterpreter.model.request.LocationRequest;
import com.thy.sensorDataInterpreter.model.response.LocationResponse;
import com.thy.sensorDataInterpreter.repository.jpa.StatisticalSensorRepository;
import com.thy.sensorDataInterpreter.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class LocationControllerTest {

    @Spy
    private LocationController controller;

    @Spy
    LocationService locationService;

    @Spy
    StatisticalSensorRepository statisticalSensorRepository;

    @BeforeEach
    public void setUp() {
        statisticalSensorRepository = mock(StatisticalSensorRepository.class);
        locationService = new LocationService(statisticalSensorRepository);
        controller = new LocationController(locationService);
    }

    @Test
    void getLocationListTest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setDeviceId("device_id");
        locationRequest.setStartTimeStamp(System.currentTimeMillis() - 10000);
        locationRequest.setFinishTimeStamp(System.currentTimeMillis());

        List<LocationResponse> responses = new ArrayList<>();
        responses.add(LocationResponse.builder()
                .deviceId("device_id-1")
                .eventTime(System.currentTimeMillis())
                .longitude(20.17)
                .latitude(45.36)
                .build());
        responses.add(LocationResponse.builder()
                .deviceId("device_id-2")
                .eventTime(System.currentTimeMillis())
                .longitude(40.17)
                .latitude(4.36)
                .build());
        when(controller.getLocationList(locationRequest)).thenReturn(responses);
        List<LocationResponse> locationList = controller.getLocationList(locationRequest);

        assertEquals(responses, locationList,
                "Expected location response list should be equal to actual location response list.");
    }
}