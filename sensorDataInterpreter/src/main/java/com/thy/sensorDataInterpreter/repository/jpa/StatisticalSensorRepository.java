package com.thy.sensorDataInterpreter.repository.jpa;

import com.thy.sensorDataInterpreter.model.response.LocationResponse;
import com.thy.sensorDataInterpreter.persistence.entity.StatisticalSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticalSensorRepository extends JpaRepository<StatisticalSensorEntity, String> {

    List<LocationResponse> findDeviceIdAndEventTimeAndGeometryTypeAndLatitudeAndLongitudeByDeviceIdAndEventTimeBetween(String deviceId, long startTime, long endTime);
}
