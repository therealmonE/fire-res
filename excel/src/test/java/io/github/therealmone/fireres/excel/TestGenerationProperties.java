package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.ReportType;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.unheated.surface.config.PrimaryGroupProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.config.SecondaryGroupProperties;
import lombok.val;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final List<Point<Integer>> FIREMODE_INTERPOLATION_POINTS = List.of(
        new IntegerPoint(0, 21),
        new IntegerPoint(1, 306),
        new IntegerPoint(18, 749),
        new IntegerPoint(21, 789),
        new IntegerPoint(26, 822),
        new IntegerPoint(48, 898),
        new IntegerPoint(49, 901),
        new IntegerPoint(70, 943)
    );

    public static final List<Point<Integer>> UNHEATED_SURFACE_INTERPOLATION_POINTS = List.of(
            new IntegerPoint(70, 154)
    );

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .includedReports(List.of(ReportType.values()))
                .environmentTemperature(21)
                .time(71)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(FireModeProperties.builder()
                .functionForm(FunctionForm.<Integer>builder()
                        .linearityCoefficient(0.3)
                        .interpolationPoints(FIREMODE_INTERPOLATION_POINTS)
                        .build())
                .thermocoupleCount(6)
                .build());

        props.putReportProperties(ExcessPressureProperties.builder()
                .basePressure(10.0)
                .delta(2.0)
                .build());

        props.putReportProperties(UnheatedSurfaceProperties.builder()
                .firstGroup(PrimaryGroupProperties.builder()
                        .functionForm(FunctionForm.<Integer>builder()
                                .interpolationPoints(UNHEATED_SURFACE_INTERPOLATION_POINTS)
                                .linearityCoefficient(1d)
                                .build())
                        .thermocoupleCount(5)
                        .build())
                .secondGroup(SecondaryGroupProperties.builder()
                        .functionForm(FunctionForm.<Integer>builder()
                                .linearityCoefficient(1d)
                                .build())
                        .thermocoupleCount(6)
                        .bound(300)
                        .build())
                .thirdGroup(SecondaryGroupProperties.builder()
                        .functionForm(FunctionForm.<Integer>builder()
                                .linearityCoefficient(1d)
                                .build())
                        .thermocoupleCount(6)
                        .bound(300)
                        .build())
                .build());

        props.putReportProperties(HeatFlowProperties.builder()
                .sensorCount(3)
                .bound(3.5)
                .build());

        setSamples(List.of(props));
    }
}
