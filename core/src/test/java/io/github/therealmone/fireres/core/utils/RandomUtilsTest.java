package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static org.junit.Assert.assertTrue;

public class RandomUtilsTest {

    public static final int CYCLES = 10000;

    @Test
    public void generatePointsInInterval() {
        for (int i = 0; i < CYCLES; i++) {
            val generatedPoints = RandomUtils.generatePointsInInterval(
                    new Point(1, 300),
                    new Point(20, 700),
                    1.0);

            assertFunctionConstantlyGrowing(generatedPoints);
        }
    }

    @Test
    public void generatePointsInIntervalWithLowNewPointChance() {
        for (int i = 0; i < CYCLES; i++) {
            val generatedPoints = RandomUtils.generatePointsInInterval(
                    new Point(1, 300),
                    new Point(20, 700),
                    0.1);

            assertFunctionConstantlyGrowing(generatedPoints);
        }
    }

    @Test
    public void generateInnerPoints() {
        for (int i = 0; i < CYCLES; i++) {
            val generatedPoints = RandomUtils.generateInnerPoints(
                    List.of(
                            new Point(0, 21),
                            new Point(1, 323),
                            new Point(5, 500),
                            new Point(8, 900)),
                    1.0);

            assertFunctionConstantlyGrowing(generatedPoints);
        }
    }

    @Test
    public void generateValueInInterval() {
        for (int i = 0; i < CYCLES; i++) {
            int generated = RandomUtils.generateValueInInterval(-100, 100);

            assertTrue(generated >= -100);
            assertTrue(generated <= 100);
        }
    }

    @Test
    public void generateValueInNegativeInterval() {
        for (int i = 0; i < CYCLES; i++) {
            int generated = RandomUtils.generateValueInInterval(-200, 0);

            assertTrue(generated >= -200);
            assertTrue(generated <= 0);
        }
    }

}
