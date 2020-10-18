package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.config.interpolation.Point;
import lombok.val;

import java.util.List;
import java.util.Random;

public class InterpolationUtils {

    private static final Random RANDOM = new Random();

    public static void addZeroPointIfNeeded(List<Point> points, Integer t0) {
        val zeroPoint = points.stream()
                .filter(point -> point.getTime().equals(0))
                .findFirst();

        if(zeroPoint.isEmpty()) {
            val delta = RANDOM.nextInt(2) - 1;
            val newPoint = new Point(0, t0 + delta);

            if (points.isEmpty()) {
                points.add(newPoint);
            } else {
                points.add(0, newPoint);
            }
        }
    }

    public static double[] getTimeArray(List<Point> points) {
        val x = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            val point = points.get(i);
            x[i] = point.getTime();
        }

        return x;
    }

    public static double[] getTemperatureArray(List<Point> points) {
        val y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            val point = points.get(i);
            y[i] = point.getTemperature();
        }

        return y;
    }

}
