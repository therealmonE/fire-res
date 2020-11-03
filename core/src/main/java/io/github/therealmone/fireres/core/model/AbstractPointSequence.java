package io.github.therealmone.fireres.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class AbstractPointSequence implements PointSequence {

    private final List<Point> value;

    public Integer getTemperature(Integer time) {
        return value.stream()
                .filter(point -> point.getTime().equals(time))
                .findAny()
                .orElseThrow()
                .getTemperature();
    }
}
