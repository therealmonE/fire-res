package io.github.therealmone.fireres.core.model.firemode;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.sequence.TemperaturePointSequence;

import java.util.List;

public class StandardTemperature extends TemperaturePointSequence {
    public StandardTemperature(List<TemperaturePoint> value) {
        super(value);
    }
}
