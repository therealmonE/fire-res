package io.github.therealmone.fireres.core.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MinAllowedTemperature extends AbstractPointSequence {

    private final List<Point> smoothedValue;

    @Builder
    public MinAllowedTemperature(List<Point> value, List<Point> smoothedValue) {
        super(value);
        this.smoothedValue = smoothedValue;
    }
}
