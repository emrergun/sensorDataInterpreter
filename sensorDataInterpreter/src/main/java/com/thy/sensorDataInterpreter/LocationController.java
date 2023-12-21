package com.thy.sensorDataInterpreter;

import com.thy.sensorDataInterpreter.model.request.LocationRequest;
import com.thy.sensorDataInterpreter.model.response.LocationResponse;
import com.thy.sensorDataInterpreter.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/locations")
    public List<LocationResponse> getLocationList(@RequestBody LocationRequest locationRequest) {
        return locationService.getLocations(locationRequest);
    }
}
