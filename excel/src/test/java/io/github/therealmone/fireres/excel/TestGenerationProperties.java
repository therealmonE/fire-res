package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.Interval;
import io.github.therealmone.fireres.core.config.ReportType;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceSecondaryGroupProperties;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final List<InterpolationPoint> FIREMODE_INTERPOLATION_POINTS = List.of(
        new InterpolationPoint(0, 21),
        new InterpolationPoint(1, 306),
        new InterpolationPoint(18, 749),
        new InterpolationPoint(21, 789),
        new InterpolationPoint(26, 822),
        new InterpolationPoint(48, 898),
        new InterpolationPoint(49, 901),
        new InterpolationPoint(70, 943)
    );

    public static final List<InterpolationPoint> UNHEATED_SURFACE_INTERPOLATION_POINTS = new ArrayList<>() {{
        val point = new InterpolationPoint();
        point.setTime(70);
        point.setInterval(new Interval(140, 150));

        add(point);
    }};

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .includedReports(List.of(ReportType.values()))
                .environmentTemperature(21)
                .time(71)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(FireModeProperties.builder()
                .linearityCoefficient(0.3)
                .interpolationPoints(FIREMODE_INTERPOLATION_POINTS)
                .thermocoupleCount(6)
                .build());

        props.putReportProperties(ExcessPressureProperties.builder()
                .basePressure(10.0)
                .delta(2.0)
                .build());

        props.putReportProperties(UnheatedSurfaceProperties.builder()
                .firstGroup(UnheatedSurfaceGroupProperties.builder()
                        .interpolationPoints(UNHEATED_SURFACE_INTERPOLATION_POINTS)
                        .linearityCoefficient(1d)
                        .thermocoupleCount(5)
                        .build())
                .secondGroup(UnheatedSurfaceSecondaryGroupProperties.builder()
                        .thermocoupleCount(6)
                        .bound(300)
                        .linearityCoefficient(1d)
                        .build())
                .thirdGroup(UnheatedSurfaceSecondaryGroupProperties.builder()
                        .thermocoupleCount(6)
                        .bound(300)
                        .linearityCoefficient(1d)
                        .build())
                .build());

        props.putReportProperties(HeatFlowProperties.builder()
                .sensorCount(3)
                .bound(3500)
                .build());

        setSamples(List.of(props));
    }
}
