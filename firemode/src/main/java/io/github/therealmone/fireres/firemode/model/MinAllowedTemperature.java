package io.github.therealmone.fireres.firemode.model;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.SmoothedIntegerPointSequence;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MinAllowedTemperature extends SmoothedIntegerPointSequence {
    @Builder
    public MinAllowedTemperature(List<IntegerPoint> value, List<IntegerPoint> smoothedValue) {
        super(value, smoothedValue);
    }
}
