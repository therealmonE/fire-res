package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.core.generator.strategy.BackwardChildFunctionGeneratorStrategy;

public class FireModeGeneratorStrategy extends BackwardChildFunctionGeneratorStrategy {

    private static final Double DELTA_COEFFICIENT = 0.7;

    @Override
    protected Integer resolveDelta(Integer t0, Integer time) {
        return t0 + (int) Math.round(Math.log(time) / Math.log(DELTA_COEFFICIENT));
    }

}
