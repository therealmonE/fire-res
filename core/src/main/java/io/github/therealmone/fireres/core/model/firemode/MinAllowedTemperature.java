package io.github.therealmone.fireres.core.model.firemode;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.sequence.SmoothedTemperaturePointSequence;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MinAllowedTemperature extends SmoothedTemperaturePointSequence {
    @Builder
    public MinAllowedTemperature(List<TemperaturePoint> value, List<TemperaturePoint> smoothedValue) {
        super(value, smoothedValue);
    }
}
