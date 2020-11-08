package io.github.therealmone.fireres.core.model.sequence;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class TemperaturePointSequence implements PointSequence<TemperaturePoint> {

    private final List<TemperaturePoint> value;

    public Integer getTemperature(Integer time) {
        return value.stream()
                .filter(point -> point.getTime().equals(time))
                .findAny()
                .orElseThrow()
                .getValue();
    }
}
