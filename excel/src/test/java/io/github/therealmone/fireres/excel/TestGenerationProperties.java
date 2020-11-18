package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.excess.pressure.ExcessPressureProperties;
import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final ArrayList<IntegerPoint> FIREMODE_INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new IntegerPoint(0, 21));
        add(new IntegerPoint(1, 306));
        add(new IntegerPoint(18, 749));
        add(new IntegerPoint(21, 789));
        add(new IntegerPoint(26, 822));
        add(new IntegerPoint(48, 898));
        add(new IntegerPoint(49, 901));
        add(new IntegerPoint(70, 943));
    }};

    public TestGenerationProperties() {

        setGeneral(GeneralProperties.builder()
                .environmentTemperature(21)
                .time(71)
                .excessPressure(ExcessPressureProperties.builder()
                        .basePressure(10.0)
                        .delta(2.0)
                        .build())
                .build());

        setSamples(List.of(SampleProperties.builder()
                .fireMode(FireModeProperties.builder()
                        .randomPoints(RandomPointsProperties.builder()
                                .enrichWithRandomPoints(true)
                                .newPointChance(0.7)
                                .build())
                        .interpolationPoints(FIREMODE_INTERPOLATION_POINTS)
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
