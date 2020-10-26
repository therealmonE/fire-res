package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.TestUtils.toPointList;
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
        val points = new ArrayList<Point>() {{
                add(new Point(1, 1));
                add(new Point(2, 2));
                add(new Point(3, 3));
        }};

        InterpolationUtils.addZeroPointIfNeeded(points, 0);

        assertEquals(4, points.size());
        assertEquals(0, points.get(0).getTime(), 0);
        assertEquals(0, points.get(0).getTemperature(), 1);
    }

    @Test
    public void smoothMaxFunction() {
        val function = toPointList(List.of(
                24, 379, 488, 555, 602, 640,
                671, 697, 719, 739, 757, 740,
                754, 767, 779, 790, 801, 811,
                820, 829, 837, 845, 853, 860,
                867, 874, 880, 887, 893, 898,
                904, 868, 873, 878, 882, 887,
                891, 896, 900, 904, 908, 912,
                916, 919, 923, 926, 930, 933,
                937, 940, 943, 946, 949, 952,
                955, 958, 961, 964, 966, 969,
                972, 974, 977, 979, 982, 984,
                987, 989, 991, 994, 996));

        val smoothedFunction = InterpolationUtils.smoothMaxFunction(function);

        assertFunctionConstantlyGrowing(smoothedFunction);
        assertEquals(function.size(), smoothedFunction.size());
        assertFunctionNotHigher(smoothedFunction, function);
    }

    @Test
    public void smoothMinFunction() {
        val function = toPointList(List.of(
                18, 280, 361, 410, 445,
                473, 496, 515, 531, 547,
                559, 550, 551, 552, 637,
                647, 655, 663, 671, 679,
                685, 692, 698, 704, 710,
                716, 720, 725, 731, 735,
                740, 786, 789, 794, 798,
                803, 800, 801, 802, 818,
                822, 825, 828, 832, 835,
                838, 842, 845, 847, 850,
                853, 856, 859, 862, 865,
                866, 869, 872, 874, 877,
                879, 882, 884, 886, 888,
                890, 893, 895, 897, 899,
                901));

        val smoothedFunction = InterpolationUtils.smoothMinFunction(function);

        assertFunctionConstantlyGrowing(smoothedFunction);
        assertEquals(function.size(), smoothedFunction.size());
        assertFunctionNotLower(smoothedFunction, function);
    }

}