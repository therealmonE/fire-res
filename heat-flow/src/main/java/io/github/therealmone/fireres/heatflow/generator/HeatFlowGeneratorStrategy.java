package io.github.therealmone.fireres.heatflow.generator;

import io.github.therealmone.fireres.core.generator.strategy.ForwardChildFunctionGeneratorStrategy;

public class HeatFlowGeneratorStrategy extends ForwardChildFunctionGeneratorStrategy {

    private static final Double DELTA_COEFFICIENT = 1.03;

    @Override
    protected Integer resolveDelta(Integer t0, Integer time) {
        return (int) Math.round(Math.log(time + 2) / Math.log(DELTA_COEFFICIENT));
    }

}
