package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleTemperature;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculateMeanValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertFunctionConstantlyGrowing(List<Integer> function) {
        for (int i = 1; i < function.size(); i++) {
            val prevValue = function.get(i - 1);
            val value = function.get(i);
            assertTrue("Comparing " + prevValue + " and " + value, value > prevValue);
        }
    }

    public static void assertThermocouplesTemperaturesEqualsMean(List<ThermocoupleTemperature> thermocouplesTemp,
                                                                 ThermocoupleMeanTemperature meanTemp) {
        val meanTempValue = meanTemp.getValue();

        assertSizesEquals(thermocouplesTemp, meanTempValue);
        IntStream.range(0, meanTempValue.size()).forEach(i -> assertEquals(
                meanTempValue.get(i),
                calculateMeanValue(thermocouplesTemp.stream().map(t -> t.getValue().get(i)).collect(Collectors.toList()))));
    }

    private static void assertSizesEquals(List<ThermocoupleTemperature> thermocouplesTemp, List<Integer> meanTemp) {
        thermocouplesTemp.forEach(thermocoupleTemperature -> {
            val thermocoupleTemperatureValue = thermocoupleTemperature.getValue();
            assertEquals(meanTemp.size(), thermocoupleTemperatureValue.size());
        });
    }

    public static void assertMeanTemperatureInInterval(List<Integer> meanTemperature, List<Integer> minAllowedTemp,
                                                       List<Integer> maxAllowedTemp) {

        assertEquals(minAllowedTemp.size(), meanTemperature.size());
        assertEquals(maxAllowedTemp.size(), meanTemperature.size());

        for (int i = 0; i < meanTemperature.size(); i++) {
            val mean = meanTemperature.get(i);
            val min = minAllowedTemp.get(i);
            val max = maxAllowedTemp.get(i);

            assertTrue( "Mean: " + mean + ", min: " + min, mean >= min);
            assertTrue("Mean: " + mean + ", max: " + max, mean <= max);
        }
    }

}
