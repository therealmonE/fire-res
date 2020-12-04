package io.github.therealmone.fireres.heatflow.generator;

import io.github.therealmone.fireres.core.generator.strategy.ForwardChildFunctionGeneratorStrategy;

public class HeatFlowGeneratorStrategy extends ForwardChildFunctionGeneratorStrategy {

    @Override
    public Integer resolveDelta(Integer t0, Integer time) {
        return (int) Math.round(Math.pow(t0, time * 0.03));
    }

}
