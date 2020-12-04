package io.github.therealmone.fireres.unheated.surface.generator;

import io.github.therealmone.fireres.core.generator.strategy.ForwardChildFunctionGeneratorStrategy;

public class UnheatedSurfaceGeneratorStrategy extends ForwardChildFunctionGeneratorStrategy {

    @Override
    protected Integer resolveDelta(Integer t0, Integer time) {
        return (int) Math.round(Math.pow(t0, time * 0.015));
    }

}
