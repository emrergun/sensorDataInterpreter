package com.thy.sensorDataInterpreter.repository.mongo;

import com.thy.sensorDataInterpreter.model.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorRepository extends MongoRepository<Sensor,Integer> {

}
