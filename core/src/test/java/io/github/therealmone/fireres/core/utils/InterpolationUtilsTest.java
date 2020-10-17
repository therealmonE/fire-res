package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.config.Point;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class InterpolationUtilsTest {

    @Test
    public void addZeroPoint() {
        val points = new ArrayList<Point>();
        InterpolationUtils.addZeroPointIfNeeded(points, 2);

        assertEquals(0, points.get(0).getTime(), 0);
        assertEquals(2, points.get(0).getTemperature(), 1);
    }

    @Test
    public void addZeroPointInNonEmptyList() {
        val points = List.
        InterpolationUtils.addZeroPointIfNeeded(points, 2);

        assertEquals(0, points.get(0).getTime(), 0);
        assertEquals(2, points.get(0).getTemperature(), 1);
    }

}