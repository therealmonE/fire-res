package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.generator.pressure.ExcessPressureGenerator;
import io.github.therealmone.fireres.core.model.pressure.SamplePressure;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcessPressureFactory {

    private final GenerationProperties generationProperties;

    public SamplePressure excessPressure() {
        return new ExcessPressureGenerator(
                generationProperties.getGeneral().getTime(),
                generationProperties.getGeneral().getExcessPressure().getDelta()
        ).generate();
    }

}
