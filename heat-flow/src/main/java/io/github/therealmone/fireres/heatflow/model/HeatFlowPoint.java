package io.github.therealmone.fireres.heatflow.model;

import io.github.therealmone.fireres.core.model.Point;

import static io.github.therealmone.fireres.heatflow.service.impl.NormalizationServiceImpl.NORMALIZATION_MULTIPLIER;

public class HeatFlowPoint extends Point<Double> {

    public HeatFlowPoint(Integer time, Double value) {
        super(time, value);
    }

    @Override
    public Double getNormalizedValue() {
        return ((int) (this.getValue() * 100)) / 100d;
    }

    @Override
    public Integer getIntValue() {
        return (int) (getValue() * NORMALIZATION_MULTIPLIER);
    }
}
