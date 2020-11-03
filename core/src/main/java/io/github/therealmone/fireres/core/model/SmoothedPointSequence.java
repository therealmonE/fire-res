package io.github.therealmone.fireres.core.model;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class SmoothedPointSequence extends AbstractPointSequence {

    private final List<Point> smoothedValue;

    public SmoothedPointSequence(List<Point> value, List<Point> smoothedValue) {
        super(value);
        this.smoothedValue = smoothedValue;
    }

    public Integer getSmoothedTemperature(Integer time) {
        return smoothedValue.stream()
                .filter(point -> point.getTime().equals(time))
                .findAny()
                .orElseThrow()
                .getTemperature();
    }

}
