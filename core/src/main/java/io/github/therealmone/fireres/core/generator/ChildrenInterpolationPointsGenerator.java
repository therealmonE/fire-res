package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.lookUpClosestPreviousPoint;
import static io.github.therealmone.fireres.core.utils.MathUtils.calculateIntsMeanValue;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Builder
public class ChildrenInterpolationPointsGenerator implements PointSequenceGenerator<IntegerPointSequence> {

    private final Integer meanValue;
    private final Integer time;
    private final List<FunctionForm<Integer>> childForms;
    private final Integer childrenCount;
    private final Integer lowerBound;
    private final Integer upperBound;
    private final Integer maxDelta;

    @Override
    public IntegerPointSequence generate() {
        val lowerBounds = getLowerBounds();
        val upperBounds = getUpperBounds(lowerBounds);
        val childrenPoints = generateRandomPoints(lowerBounds, upperBounds);

        while(getDifference(meanValue, childrenPoints) != 0) {
            adjustTemperatures(childrenPoints, lowerBounds, upperBounds);
        }

        return new IntegerPointSequence(childrenPoints.stream()
                .map(point -> new IntegerPoint(time, point))
                .collect(Collectors.toList()));
    }

    private List<Integer> getUpperBounds(List<Integer> lowerBounds) {
        return IntStream.range(0, childrenCount)
                .mapToObj(i -> Math.min(upperBound, Math.max(meanValue + maxDelta, lowerBounds.get(i))))
                .collect(Collectors.toList());
    }

    private List<Integer> getLowerBounds() {
        return IntStream.range(0, childrenCount)
                .mapToObj(i -> {
                    val previousPoint = lookUpClosestPreviousPoint(childForms.get(i).getInterpolationPoints(), time);

                    return previousPoint
                            .map(point -> Math.max(Math.max(lowerBound, point), meanValue - maxDelta))
                            .orElse(lowerBound);
                })
                .collect(Collectors.toList());
    }

    private List<Integer> generateRandomPoints(List<Integer> lowerBounds, List<Integer> upperBounds) {
        return IntStream.range(0, childrenCount)
                .mapToObj(i -> {
                    if (i % 2 != 0) {
                        return generateValueInInterval(meanValue, upperBounds.get(i));
                    } else {
                        return generateValueInInterval(lowerBounds.get(i), meanValue);
                    }
                })
                .collect(Collectors.toList());
    }

    private void adjustTemperatures(List<Integer> childrenPoints,
                                    List<Integer> lowerBounds,
                                    List<Integer> upperBounds) {

        val difference = getDifference(meanValue, childrenPoints);

        val valueIndexToAdjust = resolveValueIndexToAdjust(
                childrenPoints, difference, lowerBounds, upperBounds);

        val temperature = childrenPoints.get(valueIndexToAdjust);

        val adjust = difference < 0 ? -1 : 1;

        childrenPoints.set(valueIndexToAdjust, temperature + adjust);
    }

    private Integer resolveValueIndexToAdjust(List<Integer> childrenPoints,
                                              Integer difference,
                                              List<Integer> lowerBounds,
                                              List<Integer> upperBounds) {

        val filteredPoints = IntStream.range(0, childrenCount)
                .filter(i -> canAdjustPoint(
                        childrenPoints.get(i),
                        difference,
                        lowerBounds.get(i),
                        upperBounds.get(i)))
                .boxed()
                .collect(Collectors.toList());

        val sidedPoints = filteredPoints.stream()
                .filter(i -> chooseSide(childrenPoints.get(i), difference))
                .collect(Collectors.toList());

        val pointsToAdjust = sidedPoints.isEmpty() ? filteredPoints : sidedPoints;

        if (difference > 0) {
            return pointsToAdjust.stream()
                    .max((i1, i2) -> compareChildrenPoints(childrenPoints, i1, i2))
                    .orElseThrow(ImpossibleGenerationException::new);
        } else {
            return pointsToAdjust.stream()
                    .min((i1, i2) -> compareChildrenPoints(childrenPoints, i1, i2))
                    .orElseThrow(ImpossibleGenerationException::new);
        }
    }

    private boolean chooseSide(Integer point, Integer difference) {
        if (difference < 0) {
            return point >= meanValue;
        } else {
            return point <= meanValue;
        }
    }

    private boolean canAdjustPoint(Integer point, Integer difference,
                                   Integer lowerBound, Integer upperBound) {
        if (difference < 0) {
            return !point.equals(upperBound);
        } else {
            return !point.equals(lowerBound);
        }
    }

    private int compareChildrenPoints(List<Integer> childrenPoints, Integer i1, Integer i2) {
        val delta1 = Math.abs(childrenPoints.get(i1) - meanValue);
        val delta2 = Math.abs(childrenPoints.get(i2) - meanValue);

        return Integer.compare(delta1, delta2);
    }

    private Integer getDifference(Integer meanTemp, List<Integer> childrenPoints) {
        return meanTemp - calculateIntsMeanValue(childrenPoints);
    }

}
