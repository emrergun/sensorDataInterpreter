package com.thy.sensorDataInterpreter.persistence.entity;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Cacheable
@Table(name = "Formula")
public class FormulaEntity {

    @Id
    private int id;

    private double lowerValue;

    private double upperValue;

    private int power;

}
