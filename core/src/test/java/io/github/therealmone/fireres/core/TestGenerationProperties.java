package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.common.config.GeneralProperties;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.config.RandomPointsProperties;
import io.github.therealmone.fireres.core.common.config.SampleProperties;
import io.github.therealmone.fireres.core.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.core.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceSecondaryGroupProperties;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public TestGenerationProperties() {

        setGeneral(GeneralProperties.builder()
                .environmentTemperature(21)
                .time(71)
                .excessPressure(ExcessPressureProperties.builder()
                        .delta(2.0)
                        .build())
                .build());

        setSamples(List.of(SampleProperties.builder()
                .fireMode(FireModeProperties.builder()
                        .randomPoints(RandomPointsProperties.builder()
                                .enrichWithRandomPoints(true)
                                .newPointChance(0.7)
                                .build())
                        .interpolationPoints(TestUtils.FIREMODE_INTERPOLATION_POINTS)
                        .thermocoupleCount(6)
                        .build())
                .unheatedSurface(UnheatedSurfaceProperties.builder()
                        .firstGroup(UnheatedSurfaceGroupProperties.builder()
                                .thermocoupleCount(5)
                                .build())
                        .secondGroup(UnheatedSurfaceSecondaryGroupProperties.builder()
                                .thermocoupleCount(6)
                                .bound(300)
                                .build())
                        .thirdGroup(UnheatedSurfaceSecondaryGroupProperties.builder()
                                .thermocoupleCount(6)
                                .bound(300)
                                .build())
                        .build())
                .build()));

    }
}
