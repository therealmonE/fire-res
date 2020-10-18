package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.config.interpolation.Point;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        List<Point> points = new ArrayList<>() {{
                add(new Point(1, 1));
                add(new Point(2, 2));
                add(new Point(3, 3));
        }};

        InterpolationUtils.addZeroPointIfNeeded(points, 0);

        assertEquals(4, points.size());
        assertEquals(0, points.get(0).getTime(), 0);
        assertEquals(0, points.get(0).getTemperature(), 1);
    }

}