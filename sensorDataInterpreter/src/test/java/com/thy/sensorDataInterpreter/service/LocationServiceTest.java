package com.thy.sensorDataInterpreter.service;

import com.thy.sensorDataInterpreter.model.request.LocationRequest;
import com.thy.sensorDataInterpreter.model.response.LocationResponse;
import com.thy.sensorDataInterpreter.repository.jpa.StatisticalSensorRepository;
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
class LocationServiceTest {

    @Spy
    LocationService locationService;

    @Spy
    StatisticalSensorRepository statisticalSensorRepository;

    @BeforeEach
    public void setUp() {
        statisticalSensorRepository = mock(StatisticalSensorRepository.class);
        locationService = new LocationService(statisticalSensorRepository);
    }

    @Test
    void getLocationsTest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setDeviceId("device_id");
        locationRequest.setStartTimeStamp(System.currentTimeMillis() - 10000);
        locationRequest.setFinishTimeStamp(System.currentTimeMillis());

        List<LocationResponse> responses = new ArrayList<>();
        responses.add(LocationResponse.builder()
                .deviceId("device_id-10")
                .eventTime(System.currentTimeMillis())
                .longitude(120.68)
                .latitude(35.52)
                .build());
        responses.add(LocationResponse.builder()
                .deviceId("device_id-20")
                .eventTime(System.currentTimeMillis())
                .longitude(11.07)
                .latitude(-53.25)
                .build());
        when(locationService.getLocations(locationRequest)).thenReturn(responses);
        List<LocationResponse> locationList = locationService.getLocations(locationRequest);

        assertEquals(responses, locationList,
                "Expected location response list should be equal to actual location response list.");
    }
}