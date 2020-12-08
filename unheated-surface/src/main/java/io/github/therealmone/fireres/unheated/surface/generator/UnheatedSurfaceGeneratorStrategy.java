package io.github.therealmone.fireres.unheated.surface.generator;

import io.github.therealmone.fireres.core.generator.strategy.ForwardChildFunctionGeneratorStrategy;

public class UnheatedSurfaceGeneratorStrategy extends ForwardChildFunctionGeneratorStrategy {

    private static final Double DELTA_COEFFICIENT = 1.45;

    @Override
    protected Integer resolveDelta(Integer t0, Integer time) {
        return (int) Math.round(Math.log(time + 2) / Math.log(DELTA_COEFFICIENT));
    }

}
