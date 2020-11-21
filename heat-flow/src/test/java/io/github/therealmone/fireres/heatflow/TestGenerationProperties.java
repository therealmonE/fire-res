package io.github.therealmone.fireres.heatflow;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.heatflow.HeatFlowProperties;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final int SENSOR_COUNT = 3;
    public static final int BOUND = 3500;

    public TestGenerationProperties() {

        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        setSamples(List.of(SampleProperties.builder()
                .heatFlow(HeatFlowProperties.builder()
                        .sensorCount(SENSOR_COUNT)
                        .bound(BOUND)
                        .build())
                .build()));

    }
}
