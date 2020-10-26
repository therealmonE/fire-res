package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static Integer generateValueInInterval(Integer lowerBound, Integer upperBound) {
        if (lowerBound.equals(upperBound)) {
            return lowerBound;
        }

        return RANDOM.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static List<Point> generateInnerPoints(List<Point> points, Double newPointChance) {
        val generatedPoints = new ArrayList<Point>();

        for (int i = 1; i < points.size(); i++) {
            generatedPoints.addAll(
                    generatePointsInInterval(points.get(i - 1), points.get(i), newPointChance));
        }

        return generatedPoints;
    }
    
    public static List<Point> generatePointsInInterval(Point p1, Point p2, Double newPointChance) {
        val dy = Math.abs(p2.getTemperature() - p1.getTemperature());
        val dx = Math.abs(p2.getTime() - p1.getTime());

        val increment = dy / dx;

        return IntStream.range(1, dx)
                .mapToObj(i -> new Point(
                        p1.getTime() + i,
                        generateTemperature(p1.getTemperature(), increment, i)))
                .filter(point -> rollPoint(newPointChance))
                .collect(Collectors.toList());

    }

    private static int generateTemperature(Integer lowerBound, Integer increment, Integer i) {
        return RANDOM.nextInt(increment) + (lowerBound + increment * (i - 1)) + 1;
    }

    private static boolean rollPoint(Double newPointChance) {
        return RANDOM.nextDouble() <= newPointChance;
    }
    
    
}
