package io.github.therealmone.fireres.firemode;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final double NEW_POINT_CHANCE = 0.7;

    public static final List<InterpolationPoint> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new InterpolationPoint(0, 21));
        add(new InterpolationPoint(1, 306));
        add(new InterpolationPoint(18, 749));
        add(new InterpolationPoint(21, 789));
        add(new InterpolationPoint(26, 822));
        add(new InterpolationPoint(48, 898));
        add(new InterpolationPoint(49, 901));
        add(new InterpolationPoint(70, 943));
    }};

    public TestGenerationProperties() {

        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        setSamples(List.of(SampleProperties.builder()
                .fireMode(FireModeProperties.builder()
                        .newPointChance(NEW_POINT_CHANCE)
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .thermocoupleCount(6)
                        .build())
                .build()));

    }
}
