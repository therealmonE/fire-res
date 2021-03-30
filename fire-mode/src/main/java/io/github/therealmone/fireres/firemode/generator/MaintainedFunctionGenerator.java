package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.Point;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.interpolate;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;
import static io.github.therealmone.fireres.firemode.utils.TemperatureMaintainingUtils.generateNoise;
import static io.github.therealmone.fireres.firemode.utils.TemperatureMaintainingUtils.resolveMaintainingBounds;
import static io.github.therealmone.fireres.firemode.utils.TemperatureMaintainingUtils.selectNoisePoints;

@RequiredArgsConstructor
@Builder
public class MaintainedFunctionGenerator implements PointSequenceGenerator<IntegerPointSequence> {

    private Integer tStart;
    private Integer tEnd;

    private IntegerPointSequence lowerBound;
    private IntegerPointSequence upperBound;

    private Integer temperature;

    @Override
    public IntegerPointSequence generate() {
        val points = initializeInterpolationPoints();

        while (isFunctionOutOfBounds(points)) {
            adjustInterpolationPoints(points);
        }

        val function = interpolate(points);

        return new IntegerPointSequence(function);
    }

    private List<IntegerPoint> initializeInterpolationPoints() {
        val bounds = resolveMaintainingBounds(temperature);

        val min = bounds.getFirst();
        val max = bounds.getSecond();

        return selectNoisePoints(generateNoise(tStart, tEnd, min, max));
    }

    private void adjustInterpolationPoints(List<IntegerPoint> points) {
        val interpolatedFunction = interpolate(points);

        for (int i = 0; i < interpolatedFunction.size(); i++) {
            val functionPoint = interpolatedFunction.get(i);

            if (isPointOutOfBounds(functionPoint)) {
                generateNewPoint(points, i);
            }
        }
    }

    private boolean isFunctionOutOfBounds(List<IntegerPoint> points) {
        val interpolatedFunction = interpolate(points);

        for (int i = 0; i < interpolatedFunction.size(); i++) {
            val functionPoint = interpolatedFunction.get(i);

            if (isPointOutOfBounds(functionPoint)) {
                return true;
            }
        }

        return false;
    }

    private void generateNewPoint(List<IntegerPoint> points, Integer time) {
        val min = lowerBound.getPoint(time).getValue();
        val max = upperBound.getPoint(time).getValue();

        val newPointValue = generateValueInInterval(min, max);
        val existingPoint = lookupPoint(points, time);

        if (existingPoint.isPresent()) {
            existingPoint.get().setValue(newPointValue);
        } else {
            points.add(new IntegerPoint(time, newPointValue));
            points.sort(Comparator.comparing(Point::getTime));
        }
    }

    private Optional<IntegerPoint> lookupPoint(List<IntegerPoint> points, Integer time) {
        return points.stream()
                .filter(point -> point.getTime().equals(time))
                .findFirst();
    }

    private boolean isPointOutOfBounds(IntegerPoint functionPoint) {
        val min = lowerBound.getPoint(functionPoint.getTime()).getValue();
        val max = upperBound.getPoint(functionPoint.getTime()).getValue();

        return functionPoint.getValue() < min || functionPoint.getValue() > max;
    }

}
