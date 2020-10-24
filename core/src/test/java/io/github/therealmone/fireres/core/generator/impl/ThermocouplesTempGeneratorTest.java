package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.interpolation.InterpolationMethod;
import io.github.therealmone.fireres.core.config.interpolation.InterpolationPoints;
import io.github.therealmone.fireres.core.config.interpolation.InterpolationProperties;
import io.github.therealmone.fireres.core.config.sample.SampleProperties;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.config.random.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.temperature.Coefficient;
import io.github.therealmone.fireres.core.config.temperature.Coefficients;
import io.github.therealmone.fireres.core.config.temperature.TemperatureProperties;
import io.github.therealmone.fireres.core.factory.PointSequenceGeneratorFactory;
import lombok.val;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertMeanTemperatureInInterval;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;

public class ThermocouplesTempGeneratorTest {

    private static final Integer CYCLES = 10;

    private static final List<Point> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new Point(0, 21));
        add(new Point(1, 306));
        add(new Point(2, 392));
        add(new Point(3, 480));
        add(new Point(8, 615));
        add(new Point(18, 749));
        add(new Point(21, 789));
        add(new Point(26, 822));
        add(new Point(48, 898));
        add(new Point(49, 901));
        add(new Point(70, 943));
    }};

    @Test
    @Ignore //todo : fix
    public void generateMultipleTimes() {
        for (int i = 0; i < CYCLES; i++) {
            generate();
        }
    }

    @Test
    public void generate() {
        val factory = new PointSequenceGeneratorFactory(GenerationProperties.builder()
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
                        .interpolation(InterpolationProperties.builder()
                                .interpolationPoints(INTERPOLATION_POINTS)
                                .interpolationMethod(InterpolationMethod.LINEAR)
                                .build())
                        .thermocoupleCount(6)
                        .build()))
                .build());

        val standardTemp = factory.standardTempGenerator().generate();
        val minTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val maxTemp = factory.maxAllowedTempGenerator(standardTemp).generate();

        val meanTemp = factory.thermocoupleMeanGenerator(0).generate();

        assertMeanTemperatureInInterval(meanTemp.getValue(), minTemp.getValue(), maxTemp.getValue());
        assertFunctionConstantlyGrowing(meanTemp.getValue());

        val thermocouplesTemp = factory
                .thermocouplesTempGenerator(minTemp, maxTemp, meanTemp, 0)
                .generate();

        assertThermocouplesTemperaturesEqualsMean(thermocouplesTemp, meanTemp);
        thermocouplesTemp.forEach(t -> assertFunctionConstantlyGrowing(t.getValue()));
    }

}