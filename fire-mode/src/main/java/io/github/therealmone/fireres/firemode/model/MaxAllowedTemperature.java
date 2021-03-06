package io.github.therealmone.fireres.firemode.model;

import io.github.therealmone.fireres.core.model.IntegerBound;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MaxAllowedTemperature extends IntegerBound {
    @Builder
    public MaxAllowedTemperature(List<IntegerPoint> value) {
        super(value);
    }
}
