package com.thy.sensorDataInterpreter.service;


import com.thy.sensorDataInterpreter.model.request.LocationRequest;
import com.thy.sensorDataInterpreter.model.response.LocationResponse;
import com.thy.sensorDataInterpreter.repository.jpa.StatisticalSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final StatisticalSensorRepository statisticalSensorRepository;

    public List<LocationResponse> getLocations(LocationRequest request) {

        return statisticalSensorRepository.findDeviceIdAndEventTimeAndGeometryTypeAndLatitudeAndLongitudeByDeviceIdAndEventTimeBetween(
                request.getDeviceId(),
                request.getStartTimeStamp(),
                request.getFinishTimeStamp());
    }
}
