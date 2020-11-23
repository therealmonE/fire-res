package io.github.therealmone.fireres.heatflow.factory;

import io.github.therealmone.fireres.core.generator.strategy.IncreasingChildFunctionGeneratorStrategy;


public class HeatFlowChildFunctionGeneratorStrategy extends IncreasingChildFunctionGeneratorStrategy {

    @Override
    public int resolveDelta(Integer t0, Integer time) {
        return (int) Math.round(Math.pow(t0, time * 0.03));
    }

}
