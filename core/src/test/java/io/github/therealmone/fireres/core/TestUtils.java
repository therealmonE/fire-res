package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleTemperature;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculatePointsMeanValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertFunctionConstantlyGrowing(List<Point> function) {
        for (int i = 1; i < function.size(); i++) {
            val point = function.get(i);
            val prevPoint = function.get(i - 1);

            assertTrue("Comparing " + prevPoint + " and " + point,
                    point.getTemperature() > prevPoint.getTemperature());
        }
    }

    public static void assertThermocouplesTemperaturesEqualsMean(List<ThermocoupleTemperature> thermocouplesTemp,
                                                                 ThermocoupleMeanTemperature meanTemp) {
        val meanTempValue = meanTemp.getValue();

        assertSizesEquals(thermocouplesTemp, meanTempValue);
        IntStream.range(0, meanTempValue.size()).forEach(i -> assertEquals(
                meanTempValue.get(i).getTemperature(),
                calculatePointsMeanValue(thermocouplesTemp.stream()
                        .map(t -> t.getValue().get(i))
                        .collect(Collectors.toList()))));
    }

    private static void assertSizesEquals(List<ThermocoupleTemperature> thermocouplesTemp, List<Point> meanTemp) {
        thermocouplesTemp.forEach(thermocoupleTemperature -> {
            val thermocoupleTemperatureValue = thermocoupleTemperature.getValue();
            assertEquals(meanTemp.size(), thermocoupleTemperatureValue.size());
        });
    }

    public static void assertSizesEquals(int size, List<Point>... functions) {
        for (List<Point> function : functions) {
            assertEquals(size, function.size());
        }
    }

    public static void assertMeanTemperatureInInterval(List<Point> meanTemperature, List<Point> minAllowedTemp,
                                                       List<Point> maxAllowedTemp) {

        assertEquals(minAllowedTemp.size(), meanTemperature.size());
        assertEquals(maxAllowedTemp.size(), meanTemperature.size());

        for (int i = 0; i < meanTemperature.size(); i++) {
            val mean = meanTemperature.get(i).getTemperature();
            val min = minAllowedTemp.get(i).getTemperature();
            val max = maxAllowedTemp.get(i).getTemperature();

            assertTrue( "Mean: " + mean + ", min: " + min, mean >= min);
            assertTrue("Mean: " + mean + ", max: " + max, mean <= max);
        }
    }

    public static List<Point> toPointList(List<Integer> list) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> new Point(i, list.get(i)))
                .collect(Collectors.toList());
    }

    public static void assertFunctionNotHigher(List<Point> lowerFunction, List<Point> upperFunction) {
        lowerFunction.forEach(lowerPoint -> {
            val upperPoint = upperFunction.stream()
                    .filter(point -> point.getTime().equals(lowerPoint.getTime()))
                    .findFirst()
                    .orElseThrow();

            assertTrue("Lower: " + lowerPoint + " Upper: " + upperPoint,
                    lowerPoint.getTemperature() <= upperPoint.getTemperature());
        });
    }

    public static void assertFunctionNotLower(List<Point> upperFunction, List<Point> lowerFunction) {
        assertFunctionNotHigher(lowerFunction, upperFunction);
    }

}
