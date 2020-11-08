package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.Coefficient;
import io.github.therealmone.fireres.core.config.TemperatureProperties;
import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReportConstructorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final List<TemperaturePoint> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new TemperaturePoint(0, 21));
        add(new TemperaturePoint(1, 306));
        add(new TemperaturePoint(2, 392));
        add(new TemperaturePoint(3, 480));
        add(new TemperaturePoint(8, 615));
        add(new TemperaturePoint(18, 749));
        add(new TemperaturePoint(21, 789));
        add(new TemperaturePoint(26, 822));
        add(new TemperaturePoint(48, 898));
        add(new TemperaturePoint(49, 901));
        add(new TemperaturePoint(70, 943));
    }};

    @Test
    public void construct() throws IOException {
        val file = temporaryFolder.newFile("test.xls");

        val props = GenerationProperties.builder()
                .temperature(TemperatureProperties.builder()
                        .environmentTemperature(21)
                        .minAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 0.85),
                                new Coefficient(11, 30, 0.9),
                                new Coefficient(31, 71, 0.95)
                        ))
                        .maxAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 1.15),
                                new Coefficient(11, 30, 1.1),
                                new Coefficient(31, 71, 1.05)
                        ))
                        .build())
                .time(71)
                .samples(List.of(SampleProperties.builder()
                        .randomPoints(RandomPointsProperties.builder()
                                .enrichWithRandomPoints(true)
                                .newPointChance(1.0)
                                .build())
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .thermocoupleCount(6)
                        .build()))
                .build();

        val constructor = new ExcelReportConstructor(props);

        constructor.construct(file);
    }

}