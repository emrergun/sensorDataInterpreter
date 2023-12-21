package com.thy.sensorDataInterpreter.repository.jpa;

import com.thy.sensorDataInterpreter.persistence.entity.OperationalSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalSensorRepository extends JpaRepository<OperationalSensorEntity,String> {
}
