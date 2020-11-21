package io.github.therealmone.fireres.heatflow.model;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;

import java.util.List;

public class HeatFlowSensorTemperature extends IntegerPointSequence {
    public HeatFlowSensorTemperature(List<IntegerPoint> value) {
        super(value);
    }
}