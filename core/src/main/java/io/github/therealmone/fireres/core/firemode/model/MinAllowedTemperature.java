package io.github.therealmone.fireres.core.firemode.model;

import io.github.therealmone.fireres.core.common.model.IntegerPoint;
import io.github.therealmone.fireres.core.common.model.SmoothedIntegerPointSequence;
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
