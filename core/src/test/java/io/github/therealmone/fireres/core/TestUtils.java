package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.config.*;
import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;
import io.github.therealmone.fireres.core.config.pressure.ExcessPressureProperties;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleTemperature;
import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import io.github.therealmone.fireres.core.model.point.Point;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculatePointsMeanValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

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

    public static void assertFunctionConstantlyGrowing(List<IntegerPoint> function) {
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

    private static void assertSizesEquals(List<ThermocoupleTemperature> thermocouplesTemp,
                                          List<IntegerPoint> meanTemp) {

        thermocouplesTemp.forEach(thermocoupleTemperature -> {
            val thermocoupleTemperatureValue = thermocoupleTemperature.getValue();
            assertEquals(meanTemp.size(), thermocoupleTemperatureValue.size());
        });
    }

    public static void assertSizesEquals(int size, List<IntegerPoint>... functions) {
        for (List<IntegerPoint> function : functions) {
            assertEquals(size, function.size());
        }
    }

    public static void assertMeanTemperatureInInterval(List<IntegerPoint> meanTemperature,
                                                       List<IntegerPoint> minAllowedTemp,
                                                       List<IntegerPoint> maxAllowedTemp) {

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

    public static List<IntegerPoint> toPointList(List<Integer> list) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> new IntegerPoint(i, list.get(i)))
                .collect(Collectors.toList());
    }

    public static void assertFunctionNotHigher(List<? extends Point<?>> lowerFunction,
                                               List<? extends Point<?>> upperFunction) {

        lowerFunction.forEach(lowerPoint -> {
            val upperPoint = upperFunction.stream()
                    .filter(point -> point.getTime().equals(lowerPoint.getTime()))
                    .findFirst()
                    .orElseThrow();

            assertTrue("Lower: " + lowerPoint + " Upper: " + upperPoint,
                    lowerPoint.getValue().doubleValue() <= upperPoint.getValue().doubleValue());
        });
    }

    public static void assertFunctionNotLower(List<? extends Point<?>> upperFunction,
                                              List<? extends Point<?>> lowerFunction) {

        assertFunctionNotHigher(lowerFunction, upperFunction);
    }

    public static GenerationProperties defaultGenerationProperties() {
        return GenerationProperties.builder()
                .general(GeneralProperties.builder()
                        .environmentTemperature(21)
                        .time(71)
                        .excessPressure(ExcessPressureProperties.builder()
                                .delta(2.0)
                                .build())
                        .build())
                .samples(List.of(SampleProperties.builder()
                        .fireMode(FireModeProperties.builder()
                                .randomPoints(RandomPointsProperties.builder()
                                        .enrichWithRandomPoints(true)
                                        .newPointChance(0.7)
                                        .build())
                                .interpolationPoints(INTERPOLATION_POINTS)
                                .thermocoupleCount(6)
                                .build())
                        .build()))
                .build();
    }

    public static void assertInterpolationPoints(List<IntegerPoint> function) {
        for (IntegerPoint point : INTERPOLATION_POINTS) {
            assertEquals(point, function.stream()
                    .filter(p -> p.getTime().equals(point.getTime())).findFirst()
                    .orElseThrow());
        }
    }

}
