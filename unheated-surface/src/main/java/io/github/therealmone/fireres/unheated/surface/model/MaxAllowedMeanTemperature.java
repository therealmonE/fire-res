package io.github.therealmone.fireres.unheated.surface.model;

import io.github.therealmone.fireres.core.model.IntegerBound;
import io.github.therealmone.fireres.core.model.IntegerPoint;

import java.util.List;

public class MaxAllowedMeanTemperature extends IntegerBound {
    public MaxAllowedMeanTemperature(List<IntegerPoint> value) {
        super(value);
    }
}
