package io.github.therealmone.fireres.unheated.surface;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.core.utils.MathUtils.calculatePointsMeanValue;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertFunctionIsConstant(Number expectedValue, List<? extends Point<?>> function) {
        function.forEach(p ->
                assertEquals(expectedValue.doubleValue(), p.getValue().doubleValue(), 0));
    }

    public static void assertFunctionConstantlyGrowing(List<IntegerPoint> function) {
        for (int i = 1; i < function.size(); i++) {
            val point = function.get(i);
            val prevPoint = function.get(i - 1);

            assertTrue("Comparing " + prevPoint + " and " + point,
                    point.getValue() >= prevPoint.getValue());
        }
    }

    public static void assertThermocouplesTemperaturesEqualsMean(List<? extends IntegerPointSequence> thermocouplesTemp,
                                                                 IntegerPointSequence meanTemp) {
        val meanTempValue = meanTemp.getValue();

        assertSizesEquals(thermocouplesTemp, meanTempValue);
        IntStream.range(0, meanTempValue.size()).forEach(i -> assertEquals(
                meanTempValue.get(i).getValue(),
                calculatePointsMeanValue(thermocouplesTemp.stream()
                        .map(t -> t.getValue().get(i))
                        .collect(Collectors.toList()))));
    }

    private static void assertSizesEquals(List<? extends IntegerPointSequence> thermocouplesTemp,
                                          List<IntegerPoint> meanTemp) {

        thermocouplesTemp.forEach(thermocoupleTemperature -> {
            val thermocoupleTemperatureValue = thermocoupleTemperature.getValue();
            assertEquals(meanTemp.size(), thermocoupleTemperatureValue.size());
        });
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

    public static void assertUnheatedSurfaceReportIsValid(UnheatedSurfaceReport report) {
        assertUnheatedSurfaceGroup(report.getFirstGroup());
        assertUnheatedSurfaceGroup(report.getSecondGroup());
        assertUnheatedSurfaceGroup(report.getThirdGroup());
    }

    private static void assertUnheatedSurfaceGroup(UnheatedSurfaceGroup group) {
        val meanBound = group.getMeanBound();
        val thermocoupleBound = group.getThermocoupleBound();
        val meanTemp = group.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), constantFunction(0, TIME).getValue());

        if (meanBound != null) {
            assertFunctionNotHigher(meanTemp.getValue(), meanBound.getValue());
        } else {
            assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound.getValue());
        }

        val thermocouplesTemps = group.getThermocoupleTemperatures();

        assertThermocouplesTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals(TIME, thermocouplesTemp.getValue().size());

            assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
            assertFunctionNotLower(thermocouplesTemp.getValue(), constantFunction(0, TIME).getValue());
            assertFunctionNotHigher(thermocouplesTemp.getValue(), thermocoupleBound.getValue());
        });
    }
}
