package io.github.therealmone.fireres.firemode.model;

import io.github.therealmone.fireres.core.model.IntegerBound;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MinAllowedTemperature extends IntegerBound {
    @Builder
    public MinAllowedTemperature(List<IntegerPoint> value) {
        super(value);
    }
}
