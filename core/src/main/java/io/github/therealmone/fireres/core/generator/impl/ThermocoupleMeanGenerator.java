package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.asIntervals;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.addFirstPointIfNeeded;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.addLastPointIfNeeded;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.interpolate;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.interpolateInterval;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;
import static io.github.therealmone.fireres.core.utils.RandomUtils.rollDice;
import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Slf4j
public class ThermocoupleMeanGenerator implements PointSequenceGenerator<ThermocoupleMeanTemperature> {

    private final Integer t0;
    private final Integer time;

    private final MinAllowedTemperature minAllowedTemperature;
    private final MaxAllowedTemperature maxAllowedTemperature;

    private final InterpolationPoints interpolationPoints;
    private final Boolean enrichWithRandomPoints;
    private final Double newPointChance;

    @Override
    public ThermocoupleMeanTemperature generate() {
        log.trace("Generating thermocouple mean temperature with interpolation points: {}",
                interpolationPoints.getPoints());

        val points = new ArrayList<>(interpolationPoints.getPoints());
        adjustInterpolationPoints(points);

        if (enrichWithRandomPoints) {
            generateInnerPoints(points);
        }

        val thermocoupleMeanTemp = interpolate(points);

        log.trace("Generated thermocouple mean temperature: {}", thermocoupleMeanTemp);
        return new ThermocoupleMeanTemperature(thermocoupleMeanTemp);
    }

    private void adjustInterpolationPoints(List<Point> interpolationPoints) {
        addFirstPointIfNeeded(interpolationPoints, t0);
        addLastPointIfNeeded(interpolationPoints, time, maxAllowedTemperature, minAllowedTemperature);

        val adjustingPoints = asIntervals(interpolationPoints).stream()
                .flatMap(interval -> raiseInterval(interval, 1.0, this::intervalOutOfBounds).stream())
                .collect(Collectors.toList());

        interpolationPoints.addAll(adjustingPoints);
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
    }

    private void generateInnerPoints(List<Point> interpolationPoints) {
        val adjustingPoints = asIntervals(interpolationPoints).stream()
                .flatMap(interval ->
                        raiseInterval(interval, newPointChance, i -> true).stream())
                .collect(Collectors.toList());

        interpolationPoints.addAll(adjustingPoints);
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
    }

    private List<Point> raiseInterval(Pair<Point, Point> interval, Double newPointChance,
                                      Predicate<Pair<Point, Point>> conditionForRaising) {

        if (interval.getFirst().getTime().equals(interval.getSecond().getTime() - 1)
                || !conditionForRaising.test(interval)) {
            return emptyList();
        }

        val middlePoint = generateMiddlePoint(interval, newPointChance);

        val raisedInterval = new ArrayList<Point>();

        raisedInterval.add(middlePoint);
        raisedInterval.addAll(
                raiseInterval(new Pair<>(interval.getFirst(), middlePoint), newPointChance, conditionForRaising));
        raisedInterval.addAll(
                raiseInterval(new Pair<>(middlePoint, interval.getSecond()), newPointChance, conditionForRaising));

        raisedInterval.sort(Comparator.comparing(Point::getTime));

        return raisedInterval;
    }

    private Point generateMiddlePoint(Pair<Point, Point> interval, Double newPointChance) {
        val middleTime = (interval.getFirst().getTime() + interval.getSecond().getTime()) / 2;
        val interpolatedInterval = interpolateInterval(interval);

        val middleTemperature = interpolatedInterval.stream()
                .filter(point -> point.getTime().equals(middleTime))
                .findFirst()
                .orElseThrow()
                .getTemperature();

        if (rollDice(newPointChance)) {
            val lowerBound = Math.max(
                    minAllowedTemperature.getSmoothedTemperature(middleTime),
                    interval.getFirst().getTemperature());

            val upperBound = Math.min(
                    maxAllowedTemperature.getSmoothedTemperature(middleTime),
                    interval.getSecond().getTemperature());

            return new Point(middleTime, generateValueInInterval(lowerBound, upperBound));
        } else {
            val min = minAllowedTemperature.getSmoothedTemperature(middleTime);
            val max = maxAllowedTemperature.getSmoothedTemperature(middleTime);

            if (middleTemperature < min) {
                return new Point(middleTime, min);
            } else if (middleTemperature > max) {
                return new Point(middleTime, max);
            } else {
                return new Point(middleTime, middleTemperature);
            }
        }
    }

    private boolean outOfBounds(Point point) {
        val time = point.getTime();

        return point.getTemperature() < minAllowedTemperature.getSmoothedTemperature(time)
                || point.getTemperature() > maxAllowedTemperature.getSmoothedTemperature(time);
    }

    private boolean intervalOutOfBounds(Pair<Point, Point> interval) {
        val interpolatedInterval = interpolateInterval(interval);

        for (Point point : interpolatedInterval) {
            if (outOfBounds(point)) {
                return true;
            }
        }

        return false;
    }

}
