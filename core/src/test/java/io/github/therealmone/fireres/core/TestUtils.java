package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.config.Coefficient;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.config.TemperatureProperties;
import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleTemperature;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculatePointsMeanValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static final ArrayList<TemperaturePoint> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new TemperaturePoint(0, 21));
        add(new TemperaturePoint(1, 306));
        add(new TemperaturePoint(18, 749));
        add(new TemperaturePoint(21, 789));
        add(new TemperaturePoint(26, 822));
        add(new TemperaturePoint(48, 898));
        add(new TemperaturePoint(49, 901));
        add(new TemperaturePoint(70, 943));
    }};

    public static void assertFunctionConstantlyGrowing(List<TemperaturePoint> function) {
        for (int i = 1; i < function.size(); i++) {
            val point = function.get(i);
            val prevPoint = function.get(i - 1);

            assertTrue("Comparing " + prevPoint + " and " + point,
                    point.getValue() >= prevPoint.getValue());
        }
    }

    public static void assertThermocouplesTemperaturesEqualsMean(List<ThermocoupleTemperature> thermocouplesTemp,
                                                                 ThermocoupleMeanTemperature meanTemp) {
        val meanTempValue = meanTemp.getValue();

        assertSizesEquals(thermocouplesTemp, meanTempValue);
        IntStream.range(0, meanTempValue.size()).forEach(i -> assertEquals(
                meanTempValue.get(i).getValue(),
                calculatePointsMeanValue(thermocouplesTemp.stream()
                        .map(t -> t.getValue().get(i))
                        .collect(Collectors.toList()))));
    }

    private static void assertSizesEquals(List<ThermocoupleTemperature> thermocouplesTemp, List<TemperaturePoint> meanTemp) {
        thermocouplesTemp.forEach(thermocoupleTemperature -> {
            val thermocoupleTemperatureValue = thermocoupleTemperature.getValue();
            assertEquals(meanTemp.size(), thermocoupleTemperatureValue.size());
        });
    }

    public static void assertSizesEquals(int size, List<TemperaturePoint>... functions) {
        for (List<TemperaturePoint> function : functions) {
            assertEquals(size, function.size());
        }
    }

    public static void assertMeanTemperatureInInterval(List<TemperaturePoint> meanTemperature, List<TemperaturePoint> minAllowedTemp,
                                                       List<TemperaturePoint> maxAllowedTemp) {

        assertEquals(minAllowedTemp.size(), meanTemperature.size());
        assertEquals(maxAllowedTemp.size(), meanTemperature.size());

        for (int i = 0; i < meanTemperature.size(); i++) {
            val mean = meanTemperature.get(i).getValue();
            val min = minAllowedTemp.get(i).getValue();
            val max = maxAllowedTemp.get(i).getValue();

            assertTrue("Mean: " + mean + ", min: " + min + ", time: " + i, mean >= min);
            assertTrue("Mean: " + mean + ", max: " + max + ", time: " + i, mean <= max);
        }
    }

    public static List<TemperaturePoint> toPointList(List<Integer> list) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> new TemperaturePoint(i, list.get(i)))
                .collect(Collectors.toList());
    }

    public static void assertFunctionNotHigher(List<TemperaturePoint> lowerFunction, List<TemperaturePoint> upperFunction) {
        lowerFunction.forEach(lowerPoint -> {
            val upperPoint = upperFunction.stream()
                    .filter(point -> point.getTime().equals(lowerPoint.getTime()))
                    .findFirst()
                    .orElseThrow();

            assertTrue("Lower: " + lowerPoint + " Upper: " + upperPoint,
                    lowerPoint.getValue() <= upperPoint.getValue());
        });
    }

    public static void assertFunctionNotLower(List<TemperaturePoint> upperFunction, List<TemperaturePoint> lowerFunction) {
        assertFunctionNotHigher(lowerFunction, upperFunction);
    }

    public static GenerationProperties defaultGenerationProperties() {
        return GenerationProperties.builder()
                .temperature(TemperatureProperties.builder()
                        .environmentTemperature(21)
                        .minAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 0.85),
                                new Coefficient(11, 30, 0.9),
                                new Coefficient(31, 70, 0.95)
                        ))
                        .maxAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 1.15),
                                new Coefficient(11, 30, 1.1),
                                new Coefficient(31, 70, 1.05)
                        ))
                        .build())
                .time(71)
                .samples(List.of(SampleProperties.builder()
                        .randomPoints(RandomPointsProperties.builder()
                                .enrichWithRandomPoints(true)
                                .newPointChance(0.7)
                                .build())
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .thermocoupleCount(6)
                        .build()))
                .build();
    }

    public static void assertInterpolationPoints(List<TemperaturePoint> function) {
        for (TemperaturePoint point : INTERPOLATION_POINTS) {
            assertEquals(point, function.stream()
                    .filter(p -> p.getTime().equals(point.getTime())).findFirst()
                    .orElseThrow());
        }
    }

}
