package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.core.generator.strategy.FunctionsGenerationStrategy;

public class FireModeGenerationStrategy implements FunctionsGenerationStrategy {

    private static final Double DELTA_COEFFICIENT = 0.7;

    @Override
    public Integer resolveDelta(Integer t0, Integer time) {
        return t0 + (int) Math.round(Math.log(time) / Math.log(DELTA_COEFFICIENT));
    }

}
