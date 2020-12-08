package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.exception.MeanChildFunctionGenerationException;
import io.github.therealmone.fireres.core.generator.strategy.ChildFunctionGeneratorStrategy;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculateIntsMeanValue;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;
import static io.github.therealmone.fireres.core.utils.RandomUtils.rollDice;

@RequiredArgsConstructor
@Slf4j
public class MeanChildFunctionsGenerator implements MultiplePointSequencesGenerator<IntegerPointSequence> {

    private final Integer time;
    private final Integer t0;
    private final Integer functionsCount;

    private final IntegerPointSequence meanFunction;

    private final IntegerPointSequence lowerBound;
    private final IntegerPointSequence upperBound;

    private final ChildFunctionGeneratorStrategy strategy;
    private final Double nonLinearityCoefficient;

    @Override
    public List<IntegerPointSequence> generate() {
        val functions = initFunctions();

        val timeIterator = strategy.getTimeIterator(time);

        while (timeIterator.hasNext()) {
            val t = timeIterator.next();
            val mean = meanFunction.getPoint(t).getValue();
            val previousDeltas = getDeltas(
                    functions, meanFunction, strategy.resolvePreviousTime(t));

            val generatedValues = generateFunctionValues(
                    previousDeltas,
                    strategy.resolveLowerBounds(lowerBound, t, t0, functions, mean),
                    strategy.resolveUpperBounds(upperBound, t, t0, functions, mean),
                    mean
            );

            for (int i = 0; i < functionsCount; i++) {
                functions.get(i).getValue().add(new IntegerPoint(t, generatedValues.get(i)));
            }
        }

        functions.forEach(f -> f.getValue().sort(Comparator.comparing(Point::getTime)));
        return functions;

    }

    private List<Integer> getDeltas(List<IntegerPointSequence> functions, IntegerPointSequence mean, Integer time) {
        if (time < 0 || time.equals(this.time)) {
            return Collections.emptyList();
        }

        return functions.stream()
                .map(f -> f.getPoint(time).getValue() - mean.getPoint(time).getValue())
                .collect(Collectors.toList());
    }

    private List<IntegerPointSequence> initFunctions() {
        return new ArrayList<>() {{
            for (int i = 0; i < functionsCount; i++) {
                add(new IntegerPointSequence(new ArrayList<>()));
            }
        }};
    }

    private List<Integer> generateFunctionValues(List<Integer> previousDeltas,
                                                 List<Integer> lowerBounds, List<Integer> upperBounds,
                                                 Integer mean) {

        val temperatures = generateTemperatures(previousDeltas, lowerBounds, upperBounds, mean);

        if (getDifference(mean, temperatures) != 0) {
            adjustTemperatures(temperatures, mean, lowerBounds, upperBounds);
        }

        return temperatures;
    }

    private List<Integer> generateTemperatures(List<Integer> availableDeltas,
                                               List<Integer> lowerBounds, List<Integer> upperBounds,
                                               Integer mean) {

        if (availableDeltas.isEmpty()
                || rollDice(nonLinearityCoefficient)
                || !validDeltas(availableDeltas, lowerBounds, upperBounds, mean)) {

            return IntStream.range(0, functionsCount)
                    .mapToObj(i -> generateValueInInterval(lowerBounds.get(i), upperBounds.get(i)))
                    .collect(Collectors.toList());
        } else {
            return IntStream.range(0, functionsCount)
                    .mapToObj(i -> {
                        val delta = availableDeltas.get(i);
                        return mean + delta;
                    })
                    .collect(Collectors.toList());
        }
    }

    private boolean validDeltas(List<Integer> availableDeltas, List<Integer> lowerBounds, List<Integer> upperBounds, Integer mean) {
        for (int i = 0; i < availableDeltas.size(); i++) {
            val meanWithDelta = mean + availableDeltas.get(i);
            val min = lowerBounds.get(i);
            val max = upperBounds.get(i);

            if (meanWithDelta < min || meanWithDelta > max) {
                return false;
            }
        }

        return true;
    }

    private void adjustTemperatures(List<Integer> functionsValues, Integer mean,
                                    List<Integer> lowerBounds, List<Integer> upperBounds) {

        while (getDifference(mean, functionsValues) != 0) {
            val difference = getDifference(mean, functionsValues);

            val valueIndexToAdjust = resolveValueIndexToAdjust(
                    functionsValues, mean, difference, lowerBounds, upperBounds);

            val temperature = functionsValues.get(valueIndexToAdjust);
            val generatedAdjust = generateAdjust(
                    lowerBounds.get(valueIndexToAdjust),
                    upperBounds.get(valueIndexToAdjust),
                    difference,
                    temperature);

            functionsValues.set(valueIndexToAdjust, temperature + generatedAdjust);
        }
    }

    private Integer resolveValueIndexToAdjust(List<Integer> functionsValues, Integer mean, Integer difference,
                                              List<Integer> lowerBounds, List<Integer> upperBounds) {

        return IntStream.range(0, functionsCount)
                .filter(i -> {
                    val functionValue = functionsValues.get(i);

                    if (difference > 0) {
                        return !functionValue.equals(upperBounds.get(i));
                    } else {
                        return !functionValue.equals(lowerBounds.get(i));
                    }
                })
                .boxed()
                .max((i1, i2) -> {
                    val delta1 = Math.abs(functionsValues.get(i1) - mean);
                    val delta2 = Math.abs(functionsValues.get(i2) - mean);

                    return Integer.compare(delta2, delta1);
                })
                .orElseThrow(MeanChildFunctionGenerationException::new);
    }

    private Integer getDifference(Integer meanTemp, List<Integer> functionValues) {
        return meanTemp - calculateIntsMeanValue(functionValues);
    }

    private Integer generateAdjust(Integer lowerBound, Integer upperBound,
                                   Integer differenceToAdjust, Integer functionValue) {

        if (differenceToAdjust < 0) {
            return generateAdjustToLowerBound(lowerBound, differenceToAdjust, functionValue);
        } else {
            return generateAdjustToUpperBound(upperBound, differenceToAdjust, functionValue);
        }
    }

    private Integer generateAdjustToUpperBound(Integer upperBound, Integer differenceToAdjust, Integer functionValue) {
        if (functionValue.equals(upperBound)) {
            return 0;
        }

        val allowedAdjust = Math.min(upperBound - functionValue, differenceToAdjust);

        return generateValueInInterval(1, allowedAdjust);
    }

    private Integer generateAdjustToLowerBound(Integer lowerBound, Integer differenceToAdjust, Integer functionValue) {
        if (functionValue.equals(lowerBound)) {
            return 0;
        }

        val allowedAdjust = Math.max(lowerBound - functionValue, differenceToAdjust);

        return generateValueInInterval(allowedAdjust, -1);

    }


}
