package io.github.therealmone.fireres.core.model.sequence;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class SmoothedTemperaturePointSequence extends TemperaturePointSequence {

    private final List<TemperaturePoint> smoothedValue;

    public SmoothedTemperaturePointSequence(List<TemperaturePoint> value, List<TemperaturePoint> smoothedValue) {
        super(value);
        this.smoothedValue = smoothedValue;
    }

    public Integer getSmoothedTemperature(Integer time) {
        return smoothedValue.stream()
                .filter(point -> point.getTime().equals(time))
                .findAny()
                .orElseThrow()
                .getValue();
    }

}
