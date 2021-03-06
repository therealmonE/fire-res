package io.github.therealmone.fireres.unheated.surface;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.unheated.surface.config.PrimaryGroupProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.config.SecondaryGroupProperties;
import lombok.val;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;

    public static final int FIRST_GROUP_THERMOCOUPLES_COUNT = 5;

    public static final int SECOND_GROUP_THERMOCOUPLES_COUNT = 6;
    public static final int SECOND_GROUP_BOUND = 300;

    public static final int THIRD_GROUP_THERMOCOUPLE_COUNT = 6;
    public static final int THIRD_GROUP_BOUND = 300;

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(UnheatedSurfaceProperties.builder()
                .firstGroup(PrimaryGroupProperties.builder()
                        .thermocoupleCount(FIRST_GROUP_THERMOCOUPLES_COUNT)
                        .build())
                .secondGroup(SecondaryGroupProperties.builder()
                        .thermocoupleCount(SECOND_GROUP_THERMOCOUPLES_COUNT)
                        .bound(SECOND_GROUP_BOUND)
                        .build())
                .thirdGroup(SecondaryGroupProperties.builder()
                        .thermocoupleCount(THIRD_GROUP_THERMOCOUPLE_COUNT)
                        .bound(THIRD_GROUP_BOUND)
                        .build())
                .build());


        setSamples(List.of(props));
    }
}
