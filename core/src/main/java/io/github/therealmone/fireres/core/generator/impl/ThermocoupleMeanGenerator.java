package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.model.firemode.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleMeanTemperature;
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

    private void adjustInterpolationPoints(List<TemperaturePoint> interpolationPoints) {
        addFirstPointIfNeeded(interpolationPoints, t0);
        addLastPointIfNeeded(interpolationPoints, time, maxAllowedTemperature, minAllowedTemperature);

        val adjustingPoints = asIntervals(interpolationPoints).stream()
                .flatMap(interval -> raiseInterval(interval, 1.0, this::intervalOutOfBounds).stream())
                .collect(Collectors.toList());

        interpolationPoints.addAll(adjustingPoints);
        interpolationPoints.sort(Comparator.comparing(TemperaturePoint::getTime));
    }

    private void generateInnerPoints(List<TemperaturePoint> interpolationPoints) {
        val adjustingPoints = asIntervals(interpolationPoints).stream()
                .flatMap(interval ->
                        raiseInterval(interval, newPointChance, i -> true).stream())
                .collect(Collectors.toList());

        interpolationPoints.addAll(adjustingPoints);
        interpolationPoints.sort(Comparator.comparing(TemperaturePoint::getTime));
    }

    private List<TemperaturePoint> raiseInterval(Pair<TemperaturePoint, TemperaturePoint> interval, Double newPointChance,
                                                 Predicate<Pair<TemperaturePoint, TemperaturePoint>> conditionForRaising) {

        if (interval.getFirst().getTime().equals(interval.getSecond().getTime() - 1)
                || !conditionForRaising.test(interval)) {
            return emptyList();
        }

        val middlePoint = generateMiddlePoint(interval, newPointChance);

        val raisedInterval = new ArrayList<TemperaturePoint>();

        raisedInterval.add(middlePoint);
        raisedInterval.addAll(
                raiseInterval(new Pair<>(interval.getFirst(), middlePoint), newPointChance, conditionForRaising));
        raisedInterval.addAll(
                raiseInterval(new Pair<>(middlePoint, interval.getSecond()), newPointChance, conditionForRaising));

        raisedInterval.sort(Comparator.comparing(TemperaturePoint::getTime));

        return raisedInterval;
    }

    private TemperaturePoint generateMiddlePoint(Pair<TemperaturePoint, TemperaturePoint> interval, Double newPointChance) {
        val middleTime = (interval.getFirst().getTime() + interval.getSecond().getTime()) / 2;
        val interpolatedInterval = interpolateInterval(interval);

        val middleTemperature = interpolatedInterval.stream()
                .filter(point -> point.getTime().equals(middleTime))
                .findFirst()
                .orElseThrow()
                .getValue();

        if (rollDice(newPointChance)) {
            val lowerBound = Math.max(
                    minAllowedTemperature.getSmoothedTemperature(middleTime),
                    interval.getFirst().getValue());

            val upperBound = Math.min(
                    maxAllowedTemperature.getSmoothedTemperature(middleTime),
                    interval.getSecond().getValue());

            return new TemperaturePoint(middleTime, generateValueInInterval(lowerBound, upperBound));
        } else {
            val min = minAllowedTemperature.getSmoothedTemperature(middleTime);
            val max = maxAllowedTemperature.getSmoothedTemperature(middleTime);

            if (middleTemperature < min) {
                return new TemperaturePoint(middleTime, min);
            } else if (middleTemperature > max) {
                return new TemperaturePoint(middleTime, max);
            } else {
                return new TemperaturePoint(middleTime, middleTemperature);
            }
        }
    }

    private boolean outOfBounds(TemperaturePoint point) {
        val time = point.getTime();

        return point.getValue() < minAllowedTemperature.getSmoothedTemperature(time)
                || point.getValue() > maxAllowedTemperature.getSmoothedTemperature(time);
    }

    private boolean intervalOutOfBounds(Pair<TemperaturePoint, TemperaturePoint> interval) {
        val interpolatedInterval = interpolateInterval(interval);

        for (TemperaturePoint point : interpolatedInterval) {
            if (outOfBounds(point)) {
                return true;
            }
        }

        return false;
    }

}
