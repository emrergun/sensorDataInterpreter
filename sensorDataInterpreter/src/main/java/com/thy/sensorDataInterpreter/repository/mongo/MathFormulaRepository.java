package com.thy.sensorDataInterpreter.repository.mongo;

import com.thy.sensorDataInterpreter.persistence.entity.FormulaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MathFormulaRepository extends MongoRepository<FormulaEntity, Integer> {

    @Query("{ $and: [{lowerValue: {$lte: ?0}}, {upperValue: {$gte: ?0}}] }")
    List<Optional<FormulaEntity>> findFormula(double value);

}
