package io.github.therealmone.fireres.excess.pressure;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.excess.pressure.ExcessPressureProperties;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final double BASE_PRESSURE = 10.0;
    public static final double PRESSURE_DELTA = 2.0;
    public static final double DISPERSION_COEFFICIENT = 0.999;

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        setSamples(List.of(SampleProperties.builder()
                .excessPressure(ExcessPressureProperties.builder()
                        .basePressure(BASE_PRESSURE)
                        .delta(PRESSURE_DELTA)
                        .dispersionCoefficient(DISPERSION_COEFFICIENT)
                        .build())
                .build()));
    }
}
