package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.addPointOrAdjustExisting;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.interpolate;
import static io.github.therealmone.fireres.core.utils.TemperatureMaintainingUtils.generateNoise;
import static io.github.therealmone.fireres.core.utils.TemperatureMaintainingUtils.resolveMaintainingBounds;
import static io.github.therealmone.fireres.core.utils.TemperatureMaintainingUtils.selectNoisePoints;

@Builder
public class MaintainedFunctionGenerator implements MultiplePointSequencesGenerator<IntegerPointSequence> {

    private final Integer tStart;
    private final Integer tEnd;

    private final IntegerPointSequence lowerBound;
    private final IntegerPointSequence upperBound;

    private final Integer temperature;

    private final Integer functionsCount;

    @Override
    public List<IntegerPointSequence> generate() {
        return IntStream.range(0, functionsCount)
                .mapToObj(i -> generateFunction())
                .collect(Collectors.toList());
    }

    private IntegerPointSequence generateFunction() {
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
                generateNewPoint(points, functionPoint.getTime());
            }
        }
    }

    private boolean isFunctionOutOfBounds(List<IntegerPoint> points) {
        val interpolatedFunction = interpolate(points);

        return interpolatedFunction.stream()
                .anyMatch(this::isPointOutOfBounds);
    }

    private void generateNewPoint(List<IntegerPoint> points, Integer time) {
        val min = lowerBound.getPoint(time).getValue();
        val max = upperBound.getPoint(time).getValue();

        addPointOrAdjustExisting(points, min, max, time);
    }

    private boolean isPointOutOfBounds(IntegerPoint functionPoint) {
        val min = lowerBound.getPoint(functionPoint.getTime()).getValue();
        val max = upperBound.getPoint(functionPoint.getTime()).getValue();

        return functionPoint.getValue() < min || functionPoint.getValue() > max;
    }

}
