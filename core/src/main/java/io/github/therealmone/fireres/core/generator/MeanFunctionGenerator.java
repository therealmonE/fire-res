package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
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
public class MeanFunctionGenerator implements PointSequenceGenerator<IntegerPointSequence> {

    private final Integer t0;
    private final Integer time;

    private final IntegerPointSequence upperBound;
    private final IntegerPointSequence lowerBound;

    private final Interpolation interpolation;

    @Override
    public IntegerPointSequence generate() {
        val points = interpolation.getInterpolationPoints().stream()
                .map(p -> new IntegerPoint(p.getTime(), p.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));

        adjustInterpolationPoints(points);
        generateInnerPoints(points);

        val thermocoupleMeanTemp = interpolate(points);

        return new IntegerPointSequence(thermocoupleMeanTemp);
    }

    private void adjustInterpolationPoints(List<IntegerPoint> interpolationPoints) {
        addFirstPointIfNeeded(interpolationPoints, t0);
        addLastPointIfNeeded(interpolationPoints, time, lowerBound, upperBound);

        val adjustingPoints = asIntervals(interpolationPoints).stream()
                .flatMap(interval -> raiseInterval(interval, 1.0, 1.0, this::intervalOutOfBounds)
                        .stream())
                .collect(Collectors.toList());

        interpolationPoints.addAll(adjustingPoints);
        interpolationPoints.sort(Comparator.comparing(IntegerPoint::getTime));
    }

    private void generateInnerPoints(List<IntegerPoint> interpolationPoints) {
        val adjustingPoints = asIntervals(interpolationPoints).stream()
                .flatMap(interval ->
                        raiseInterval(interval, interpolation.getNewPointChance(), interpolation.getDispersionCoefficient(), i -> true)
                                .stream())
                .collect(Collectors.toList());

        interpolationPoints.addAll(adjustingPoints);
        interpolationPoints.sort(Comparator.comparing(IntegerPoint::getTime));
    }

    private List<IntegerPoint> raiseInterval(Pair<IntegerPoint, IntegerPoint> interval,
                                             Double newPointChance, Double dispersion,
                                             Predicate<Pair<IntegerPoint, IntegerPoint>> conditionForRaising) {

        if (interval.getFirst().getTime().equals(interval.getSecond().getTime() - 1)
                || !conditionForRaising.test(interval)) {
            return emptyList();
        }

        val middlePoint = generateMiddlePoint(interval, newPointChance, dispersion);

        val raisedInterval = new ArrayList<IntegerPoint>();

        raisedInterval.add(middlePoint);
        raisedInterval.addAll(
                raiseInterval(new Pair<>(interval.getFirst(), middlePoint), newPointChance, dispersion, conditionForRaising));
        raisedInterval.addAll(
                raiseInterval(new Pair<>(middlePoint, interval.getSecond()), newPointChance, dispersion, conditionForRaising));

        raisedInterval.sort(Comparator.comparing(IntegerPoint::getTime));

        return raisedInterval;
    }

    private IntegerPoint generateMiddlePoint(Pair<IntegerPoint, IntegerPoint> interval, Double newPointChance, Double dispersion) {
        val middleTime = (interval.getFirst().getTime() + interval.getSecond().getTime()) / 2;
        val interpolatedInterval = interpolateInterval(interval);

        val middleTemperature = interpolatedInterval.stream()
                .filter(point -> point.getTime().equals(middleTime))
                .findFirst()
                .orElseThrow()
                .getValue();

        val min = Math.max(
                this.lowerBound.getPoint(middleTime).getValue(),
                interval.getFirst().getValue());

        val max = Math.min(
                this.upperBound.getPoint(middleTime).getValue(),
                interval.getSecond().getValue());

        val mean = (max + min) / 2;

        if (rollDice(newPointChance)) {
            return new IntegerPoint(middleTime, generateValueInInterval(
                    mean - (int) ((mean - min) * dispersion),
                    mean + (int) ((max - mean) * dispersion)
            ));
        } else {
            if (middleTemperature < min) {
                return new IntegerPoint(middleTime, min);
            } else if (middleTemperature > max) {
                return new IntegerPoint(middleTime, max);
            } else {
                return new IntegerPoint(middleTime, middleTemperature);
            }
        }
    }

    private boolean outOfBounds(IntegerPoint point) {
        val time = point.getTime();

        return point.getValue() > upperBound.getPoint(time).getValue()
                || point.getValue() < lowerBound.getPoint(time).getValue();
    }

    private boolean intervalOutOfBounds(Pair<IntegerPoint, IntegerPoint> interval) {
        val interpolatedInterval = interpolateInterval(interval);

        for (IntegerPoint point : interpolatedInterval) {
            if (outOfBounds(point)) {
                return true;
            }
        }

        return false;
    }

}
