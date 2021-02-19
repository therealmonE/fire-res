package io.github.therealmone.fireres.heatflow.model;

import io.github.therealmone.fireres.core.model.PointSequence;

import java.util.List;

public class HeatFlowPointSequence extends PointSequence<HeatFlowPoint> {
    public HeatFlowPointSequence(List<HeatFlowPoint> value) {
        super(value);
    }
}
