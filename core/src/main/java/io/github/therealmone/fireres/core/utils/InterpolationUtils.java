package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.Point;
import lombok.val;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

public class InterpolationUtils {

    private static final Random RANDOM = new Random();

    public static void addFirstPointIfNeeded(List<IntegerPoint> points, Integer t0) {
        val firstPoint = points.stream()
                .filter(point -> point.getTime().equals(0))
                .findFirst();

        if(firstPoint.isEmpty()) {
            val delta = RANDOM.nextInt(2) - 1;
            val newPoint = new IntegerPoint(0, t0 + delta);

            if (points.isEmpty()) {
                points.add(newPoint);
            } else {
                points.add(0, newPoint);
            }
        }
    }

    public static void addLastPointIfNeeded(List<IntegerPoint> points, Integer time,
                                            IntegerPointSequence lowerBound,
                                            IntegerPointSequence upperBound) {

        val lastPoint = points.stream()
                .filter(point -> point.getTime().equals(time - 1))
                .findFirst();

        if(lastPoint.isEmpty()) {
            val closestPoint = points.stream()
                    .filter(p -> p.getTime() < time)
                    .max(Comparator.comparing(Point::getTime))
                    .orElseThrow();

            val min = Math.max(closestPoint.getValue(), lowerBound.getPoint(time - 1).getValue());
            val max = upperBound.getPoint(time - 1).getValue();

            val newPoint = new IntegerPoint(time - 1, generateValueInInterval(min, max));

            points.add(points.size(), newPoint);
        }
    }

    public static double[] getTimeArray(List<IntegerPoint> points) {
        val x = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            val point = points.get(i);
            x[i] = point.getTime();
        }

        return x;
    }

    public static double[] getValueArray(List<IntegerPoint> points) {
        val y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            val point = points.get(i);
            y[i] = point.getValue();
        }

        return y;
    }

    public static List<IntegerPoint> smoothMinFunction(List<IntegerPoint> function) {
        val smoothedFunctions = new ArrayList<>(function);

        removePits(smoothedFunctions);

        return interpolate(smoothedFunctions);
    }

    public static List<IntegerPoint> smoothMaxFunction(List<IntegerPoint> function) {
        val smoothedFunctions = new ArrayList<>(function);

        removePeaks(smoothedFunctions);

        return interpolate(smoothedFunctions);
    }

    public static List<IntegerPoint> interpolateInterval(Pair<IntegerPoint, IntegerPoint> interval) {
        return interpolate(List.of(interval.getFirst(), interval.getSecond()));
    }

    public static List<IntegerPoint> interpolate(List<IntegerPoint> function) {
        val interpolator = new LinearInterpolator();
        val interpolation = interpolator.interpolate(
                getTimeArray(function),
                getValueArray(function));

        return IntStream.range(function.get(0).getTime(), function.get(function.size() - 1).getTime() + 1)
                .mapToObj(x -> new IntegerPoint(x, (int) Math.round(interpolation.value(x))))
                .collect(Collectors.toList());
    }

    private static void removePeaks(List<IntegerPoint> function) {
        val pointsToRemove = new ArrayList<IntegerPoint>();

        for (int i = 0; i < function.size() - 1; i++) {
            val point = function.get(i);
            val nextPoint = function.get(i + 1);

            if (point.getValue() > nextPoint.getValue()) {
                for (int j = i; j >= 0; j--) {
                    if (function.get(j).getValue() >= nextPoint.getValue() - (i - j)) {
                        pointsToRemove.add(function.get(j));
                    } else {
                        break;
                    }
                }
            }
        }

        pointsToRemove.forEach(function::remove);
    }

    private static void removePits(List<IntegerPoint> function) {
        val pointsToRemove = new ArrayList<IntegerPoint>();

        for (int i = 0; i < function.size() - 1; i++) {
            val point = function.get(i);

            var nextPoint = function.get(i + 1);

            while (nextPoint.getValue() < point.getValue()) {
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
