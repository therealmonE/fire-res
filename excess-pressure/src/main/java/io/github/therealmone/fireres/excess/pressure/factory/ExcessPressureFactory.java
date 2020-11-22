package io.github.therealmone.fireres.excess.pressure.factory;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excess.pressure.generator.ExcessPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.generator.MaxAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.generator.MinAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.SamplePressure;

public class ExcessPressureFactory {

    @Inject
    private GenerationProperties generationProperties;

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
                maxAllowedPressure,
                generationProperties.getGeneral().getExcessPressure().getDispersionCoefficient()
        ).generate();
    }

}
