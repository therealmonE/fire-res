package io.github.therealmone.fireres.excess.pressure.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excess.pressure.generator.ExcessPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.generator.MaxAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.generator.MinAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.SamplePressure;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcessPressureFactory {

    private final GenerationProperties generationProperties;

    public MinAllowedPressure minAllowedPressure() {
        return new MinAllowedPressureGenerator(
                generationProperties.getGeneral().getTime(),
                generationProperties.getGeneral().getExcessPressure().getDelta()
        ).generate();
    }

    public MaxAllowedPressure maxAllowedPressure() {
        return new MaxAllowedPressureGenerator(
                generationProperties.getGeneral().getTime(),
                generationProperties.getGeneral().getExcessPressure().getDelta()
        ).generate();
    }

    public SamplePressure excessPressure(MinAllowedPressure minAllowedPressure,
                                         MaxAllowedPressure maxAllowedPressure) {

        return new ExcessPressureGenerator(
                generationProperties.getGeneral().getTime(),
                minAllowedPressure,
                maxAllowedPressure
        ).generate();
    }

}
