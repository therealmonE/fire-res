package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.common.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.common.report.FullReport;
import io.github.therealmone.fireres.core.common.model.IntegerPoint;
import io.github.therealmone.fireres.core.common.model.Point;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceGroup;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.core.utils.MathUtils.calculatePointsMeanValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static final ArrayList<IntegerPoint> FIREMODE_INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new IntegerPoint(0, 21));
        add(new IntegerPoint(1, 306));
        add(new IntegerPoint(18, 749));
        add(new IntegerPoint(21, 789));
        add(new IntegerPoint(26, 822));
        add(new IntegerPoint(48, 898));
        add(new IntegerPoint(49, 901));
        add(new IntegerPoint(70, 943));
    }};

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

    public static void assertSizesEquals(int size, List<? extends Point<?>>... functions) {
        for (List<? extends Point<?>> function : functions) {
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

    public static void assertInterpolationPoints(List<IntegerPoint> function) {
        for (IntegerPoint point : FIREMODE_INTERPOLATION_POINTS) {
            assertEquals(point, function.stream()
                    .filter(p -> p.getTime().equals(point.getTime())).findFirst()
                    .orElseThrow());
        }
    }

    public static void assertFireMode(FullReport report) {
        val time = (int) report.getTime();
        val environmentTemp = (int) report.getEnvironmentTemperature();
        val furnaceTemp = report.getFireMode().getFurnaceTemperature().getValue();
        val minAllowedTemp = report.getFireMode().getMinAllowedTemperature().getValue();
        val maxAllowedTemp = report.getFireMode().getMaxAllowedTemperature().getValue();
        val maxAllowedSmoothedTemp = report.getFireMode().getMaxAllowedTemperature().getSmoothedValue();
        val standardTemp = report.getFireMode().getStandardTemperature().getValue();

        assertEquals(71, time);
        assertEquals(21, environmentTemp);

        //noinspection unchecked
        assertSizesEquals(time, furnaceTemp, minAllowedTemp, maxAllowedTemp, maxAllowedSmoothedTemp, standardTemp);

        assertFunctionConstantlyGrowing(minAllowedTemp);
        assertFunctionConstantlyGrowing(maxAllowedSmoothedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedSmoothedTemp);

        assertFunctionNotLower(standardTemp, minAllowedTemp);
        assertFunctionNotHigher(standardTemp, maxAllowedTemp);
        assertFunctionNotHigher(standardTemp, maxAllowedSmoothedTemp);

        report.getFireMode().getSamples().forEach(sample -> {
            val meanTemp = sample.getThermocoupleMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemp.getValue());
            assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedSmoothedTemp);

            val thermocouplesTemps = sample.getThermocoupleTemperatures();

            assertEquals(6, thermocouplesTemps.size());
            assertThermocouplesTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                assertEquals(time, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedSmoothedTemp);

            });
        });
    }

    public static void assertExcessPressure(FullReport report) {
        val time = (int) report.getTime();

        val min = report.getExcessPressure().getMinAllowedPressure();
        val max = report.getExcessPressure().getMaxAllowedPressure();

        assertFunctionIsConstant(-2, min.getValue());
        assertFunctionIsConstant(2, max.getValue());

        assertSizesEquals(time, min.getValue(), max.getValue());

        report.getExcessPressure().getSamples().forEach(sample -> {
            val pressure = sample.getPressure();

            assertFunctionNotHigher(pressure.getValue(), max.getValue());
            assertFunctionNotLower(pressure.getValue(), min.getValue());
        });
    }

    public static void assertUnheatedSurface(FullReport report) {
        val time = (int) report.getTime();

        report.getUnheatedSurface().getSamples().forEach(sample -> {
            assertUnheatedSurfaceGroup(sample.getFirstGroup(), time);
            assertUnheatedSurfaceGroup(sample.getSecondGroup(), time);
            assertUnheatedSurfaceGroup(sample.getThirdGroup(), time);
        });
    }

    private static void assertUnheatedSurfaceGroup(UnheatedSurfaceGroup group, Integer time) {
        val meanBound = group.getMeanBound();
        val thermocoupleBound = group.getThermocoupleBound();
        val meanTemp = group.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), constantFunction(0, time).getValue());

        if (meanBound != null) {
            assertFunctionNotHigher(meanTemp.getValue(), meanBound.getValue());
        } else {
            assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound.getValue());
        }

        val thermocouplesTemps = group.getThermocoupleTemperatures();

        assertThermocouplesTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals((int) time, thermocouplesTemp.getValue().size());

            assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
            assertFunctionNotLower(thermocouplesTemp.getValue(), constantFunction(0, time).getValue());
            assertFunctionNotHigher(thermocouplesTemp.getValue(), thermocoupleBound.getValue());
        });
    }
}
