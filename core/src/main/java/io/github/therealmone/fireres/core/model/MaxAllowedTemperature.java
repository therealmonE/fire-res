package io.github.therealmone.fireres.core.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MaxAllowedTemperature extends SmoothedPointSequence {
    @Builder
    public MaxAllowedTemperature(List<Point> value, List<Point> smoothedValue) {
        super(value, smoothedValue);
    }
}
