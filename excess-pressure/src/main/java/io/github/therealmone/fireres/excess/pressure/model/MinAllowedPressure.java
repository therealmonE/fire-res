package io.github.therealmone.fireres.excess.pressure.model;

import io.github.therealmone.fireres.core.model.DoubleBound;
import io.github.therealmone.fireres.core.model.DoublePoint;

import java.util.List;

public class MinAllowedPressure extends DoubleBound {
    public MinAllowedPressure(List<DoublePoint> value) {
        super(value);
    }
}
