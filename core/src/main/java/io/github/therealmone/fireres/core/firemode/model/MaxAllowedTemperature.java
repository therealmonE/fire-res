package io.github.therealmone.fireres.core.firemode.model;

import io.github.therealmone.fireres.core.common.model.IntegerPoint;
import io.github.therealmone.fireres.core.common.model.SmoothedIntegerPointSequence;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MaxAllowedTemperature extends SmoothedIntegerPointSequence {
    @Builder
    public MaxAllowedTemperature(List<IntegerPoint> value, List<IntegerPoint> smoothedValue) {
        super(value, smoothedValue);
    }
}
