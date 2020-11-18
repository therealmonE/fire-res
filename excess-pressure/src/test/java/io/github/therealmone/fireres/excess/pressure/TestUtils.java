package io.github.therealmone.fireres.excess.pressure;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertFunctionIsConstant(Number expectedValue, List<? extends Point<?>> function) {
        function.forEach(p ->
                assertEquals(expectedValue.doubleValue(), p.getValue().doubleValue(), 0));
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

    public static void assertSizesEquals(int size, List<? extends Point<?>>... functions) {
        for (List<? extends Point<?>> function : functions) {
            assertEquals(size, function.size());
        }
    }
}
