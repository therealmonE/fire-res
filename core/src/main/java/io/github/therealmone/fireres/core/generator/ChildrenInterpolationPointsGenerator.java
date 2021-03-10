package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.generator.strategy.FunctionsGenerationStrategy;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.utils.RandomUtils;
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
        val upperBounds = getUpperBounds();
        val childrenPoints = generateRandomPoints(lowerBounds, upperBounds);

        while(getDifference(meanValue, childrenPoints) != 0) {
            adjustTemperatures(childrenPoints, lowerBounds, upperBounds);
        }

        return new IntegerPointSequence(childrenPoints.stream()
                .map(point -> new IntegerPoint(time, point))
                .collect(Collectors.toList()));
    }

    private List<Integer> getUpperBounds() {
        return IntStream.range(0, childrenCount)
                .mapToObj(i -> upperBound)
                .collect(Collectors.toList());
    }

    private List<Integer> getLowerBounds() {
        return IntStream.range(0, childrenCount)
                .mapToObj(i -> {
                    val previousPoint = lookUpClosestPreviousPoint(childForms.get(i).getInterpolationPoints(), time);

                    return previousPoint
                            .map(point -> Math.max(lowerBound, point))
                            .orElse(lowerBound);
                })
                .collect(Collectors.toList());
    }

    private List<Integer> generateRandomPoints(List<Integer> lowerBounds, List<Integer> upperBounds) {
        return IntStream.range(0, childrenCount)
                .mapToObj(i -> RandomUtils.generateValueInInterval(lowerBounds.get(i), upperBounds.get(i)))
                .collect(Collectors.toList());
    }

    private void adjustTemperatures(List<Integer> childrenPoints,
                                    List<Integer> lowerBounds,
                                    List<Integer> upperBounds) {

        val difference = getDifference(meanValue, childrenPoints);

        val valueIndexToAdjust = resolveValueIndexToAdjust(
                childrenPoints, difference, lowerBounds, upperBounds);

        val temperature = childrenPoints.get(valueIndexToAdjust);

        val generatedAdjust = generateAdjust(
                lowerBounds.get(valueIndexToAdjust),
                upperBounds.get(valueIndexToAdjust),
                difference,
                temperature);

        childrenPoints.set(valueIndexToAdjust, temperature + generatedAdjust);
    }

    private Integer resolveValueIndexToAdjust(List<Integer> childrenPoints,
                                              Integer difference,
                                              List<Integer> lowerBounds,
                                              List<Integer> upperBounds) {

        return IntStream.range(0, childrenCount)
                .filter(i -> {
                    val functionValue = childrenPoints.get(i);

                    if (difference > 0) {
                        return !functionValue.equals(upperBounds.get(i));
                    } else {
                        return !functionValue.equals(lowerBounds.get(i));
                    }
                })
                .boxed()
                .max((i1, i2) -> {
                    val delta1 = Math.abs(childrenPoints.get(i1) - meanValue);
                    val delta2 = Math.abs(childrenPoints.get(i2) - meanValue);

                    return Integer.compare(delta2, delta1);
                })
                .orElseThrow(ImpossibleGenerationException::new);
    }

    private Integer getDifference(Integer meanTemp, List<Integer> childrenPoints) {
        return meanTemp - calculateIntsMeanValue(childrenPoints);
    }

    private Integer generateAdjust(Integer lowerBound,
                                   Integer upperBound,
                                   Integer differenceToAdjust,
                                   Integer childValue) {

        if (differenceToAdjust < 0) {
            return generateAdjustToLowerBound(lowerBound, differenceToAdjust, childValue);
        } else {
            return generateAdjustToUpperBound(upperBound, differenceToAdjust, childValue);
        }
    }

    private Integer generateAdjustToUpperBound(Integer upperBound,
                                               Integer differenceToAdjust,
                                               Integer functionValue) {

        if (functionValue.equals(upperBound)) {
            return 0;
        }

        val allowedAdjust = Math.min(upperBound - functionValue, differenceToAdjust);

        return generateValueInInterval(1, allowedAdjust);
    }

    private Integer generateAdjustToLowerBound(Integer lowerBound,
                                               Integer differenceToAdjust,
                                               Integer functionValue) {

        if (functionValue.equals(lowerBound)) {
            return 0;
        }

        val allowedAdjust = Math.max(lowerBound - functionValue, differenceToAdjust);

        return generateValueInInterval(allowedAdjust, -1);
    }

}
