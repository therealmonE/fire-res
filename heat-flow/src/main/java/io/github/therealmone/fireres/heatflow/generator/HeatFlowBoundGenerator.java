package io.github.therealmone.fireres.heatflow.generator;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.heatflow.model.HeatFlowBound;
import lombok.RequiredArgsConstructor;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

@RequiredArgsConstructor
public class HeatFlowBoundGenerator implements PointSequenceGenerator<HeatFlowBound> {

    private final Integer time;
    private final Integer bound;

    @Override
    public HeatFlowBound generate() {
        return new HeatFlowBound(constantFunction(time, bound).getValue());
    }

}
