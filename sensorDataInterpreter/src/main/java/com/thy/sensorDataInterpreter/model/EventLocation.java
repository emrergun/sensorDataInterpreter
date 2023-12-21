package com.thy.sensorDataInterpreter.model;

import lombok.*;

@Builder
@ToString
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventLocation {
    private Geometry geometry;
}