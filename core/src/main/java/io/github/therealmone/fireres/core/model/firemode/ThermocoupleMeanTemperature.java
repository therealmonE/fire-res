package io.github.therealmone.fireres.core.model.firemode;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.sequence.TemperaturePointSequence;

import java.util.List;

public class ThermocoupleMeanTemperature extends TemperaturePointSequence {
    public ThermocoupleMeanTemperature(List<TemperaturePoint> value) {
        super(value);
    }
}
