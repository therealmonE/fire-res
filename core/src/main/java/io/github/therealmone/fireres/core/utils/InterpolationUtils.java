package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Point;
import lombok.val;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

public class InterpolationUtils {

    private static final Random RANDOM = new Random();

    public static void addFirstPointIfNeeded(List<Point> points, Integer t0) {
        val firstPoint = points.stream()
                .filter(point -> point.getTime().equals(0))
                .findFirst();

        if(firstPoint.isEmpty()) {
            val delta = RANDOM.nextInt(2) - 1;
            val newPoint = new Point(0, t0 + delta);

            if (points.isEmpty()) {
                points.add(newPoint);
            } else {
                points.add(0, newPoint);
            }
        }
    }

    public static void addLastPointIfNeeded(List<Point> points, Integer time,
                                            MaxAllowedTemperature maxAllowedTemperature,
                                            MinAllowedTemperature minAllowedTemperature) {

        val lastPoint = points.stream()
                .filter(point -> point.getTime().equals(time - 1))
                .findFirst();

        if(lastPoint.isEmpty()) {
            val min = minAllowedTemperature.getSmoothedTemperature(time - 1);
            val max = maxAllowedTemperature.getSmoothedTemperature(time - 1);

            val newPoint = new Point(time - 1, generateValueInInterval(min, max));

            points.add(points.size(), newPoint);
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

    public static List<Point> smoothMinFunction(List<Point> function) {
        val smoothedFunctions = new ArrayList<>(function);

        removePits(smoothedFunctions);

        return interpolate(smoothedFunctions);
    }

    public static List<Point> smoothMaxFunction(List<Point> function) {
        val smoothedFunctions = new ArrayList<>(function);

        removePeaks(smoothedFunctions);

        return interpolate(smoothedFunctions);
    }

    public static List<Point> interpolateInterval(Pair<Point, Point> interval) {
        return interpolate(List.of(interval.getFirst(), interval.getSecond()));
    }

    public static List<Point> interpolate(List<Point> function) {
        val interpolator = new LinearInterpolator();
        val interpolation = interpolator.interpolate(
                getTimeArray(function),
                getTemperatureArray(function));

        return IntStream.range(function.get(0).getTime(), function.get(function.size() - 1).getTime() + 1)
                .mapToObj(x -> new Point(x, (int) Math.round(interpolation.value(x))))
                .collect(Collectors.toList());
    }

    private static void removePeaks(List<Point> function) {
        val pointsToRemove = new ArrayList<Point>();

        for (int i = 0; i < function.size() - 1; i++) {
            val point = function.get(i);
            val nextPoint = function.get(i + 1);

            if (point.getTemperature() > nextPoint.getTemperature()) {
                for (int j = i; j >= 0; j--) {
                    if (function.get(j).getTemperature() >= nextPoint.getTemperature() - (i - j)) {
                        pointsToRemove.add(function.get(j));
                    } else {
                        break;
                    }
                }
            }
        }

        pointsToRemove.forEach(function::remove);
    }

    private static void removePits(List<Point> function) {
        val pointsToRemove = new ArrayList<Point>();

        for (int i = 0; i < function.size() - 1; i++) {
            val point = function.get(i);

            var nextPoint = function.get(i + 1);

            while (nextPoint.getTemperature() < point.getTemperature()) {
                pointsToRemove.add(nextPoint);

                if (nextPoint.getTime().equals(function.size() - 1)) {
                    break;
                } else {
                    nextPoint = function.get(nextPoint.getTime() + 1);
                }
            }
        }

        pointsToRemove.forEach(function::remove);
    }

}
