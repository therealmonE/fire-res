package io.github.therealmone.fireres.firemode;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final double LINEAR_COEFFICIENT = 0.3;

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

        val props = new SampleProperties();

        props.putReportProperties(FireModeProperties.builder()
                .linearityCoefficient(LINEAR_COEFFICIENT)
                .interpolationPoints(INTERPOLATION_POINTS)
                .thermocoupleCount(6)
                .build());

        setSamples(List.of(props));
    }
}
