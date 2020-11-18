package io.github.therealmone.fireres.firemode;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final boolean ENRICH_WITH_RANDOM_POINTS = true;
    public static final double NEW_POINT_CHANCE = 0.7;

    public static final ArrayList<IntegerPoint> INTERPOLATION_POINTS = new ArrayList<>() {{
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
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        setSamples(List.of(SampleProperties.builder()
                .fireMode(FireModeProperties.builder()
                        .randomPoints(RandomPointsProperties.builder()
                                .enrichWithRandomPoints(ENRICH_WITH_RANDOM_POINTS)
                                .newPointChance(NEW_POINT_CHANCE)
                                .build())
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .thermocoupleCount(6)
                        .build())
                .build()));

    }
}
