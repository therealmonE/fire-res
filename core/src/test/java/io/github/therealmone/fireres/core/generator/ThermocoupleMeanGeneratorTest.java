package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationMethod;
import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.config.Point;
import io.github.therealmone.fireres.core.factory.NumberSequenceGeneratorFactory;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThermocoupleMeanGeneratorTest {

    private static final InterpolationPoints INTERPOLATION_POINTS = new InterpolationPoints(List.of(
            new Point(0, 21),
            new Point(1, 306),
            new Point(18, 707),
            new Point(21, 772),
            new Point(26, 812),
            new Point(48, 861),
            new Point(49, 892),
            new Point(70, 943)
    ));

    @Test
    public void linearInterpolationMethod() {
        val factory = new NumberSequenceGeneratorFactory(GenerationProperties.builder()
                .time(71)
                .interpolationPoints(INTERPOLATION_POINTS)
                .interpolationMethod(InterpolationMethod.LINEAR)
                .build());

        val thermocoupleMeanTemp = factory.thermocoupleMeanGenerator().generate().getValue();
        thermocoupleMeanTemp.forEach(System.out::println);

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    private void assertInterpolationPoints(List<Integer> function) {
        for (Point point : INTERPOLATION_POINTS.getPoints()) {
            assertEquals(point.getTemperature(), function.get(point.getTime()));
        }
    }

    private void assertFunctionConstantlyGrowing(List<Integer> function) {
        for (int i = 1; i < function.size(); i++) {
            val prevValue = function.get(i - 1);
            val value = function.get(i);
            assertTrue(value > prevValue);
        }
    }

}