package io.github.therealmone.fireres.core.common.generator;

import io.github.therealmone.fireres.core.exception.InvalidMeanFunctionException;
import io.github.therealmone.fireres.core.exception.MeanChildFunctionGenerationException;
import io.github.therealmone.fireres.core.common.model.IntegerPoint;
import io.github.therealmone.fireres.core.common.model.IntegerPointSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculateIntsMeanValue;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Slf4j
public class MeanChildFunctionsGenerator implements MultiplePointSequencesGenerator<IntegerPointSequence> {

    private static final Integer ATTEMPTS = 100;

    private final Integer time;
    private final Integer t0;

    private final IntegerPointSequence meanFunction;
    private final IntegerPointSequence upperBound;
    private final IntegerPointSequence lowerBound;

    private final Integer functionsCount;

    @Override
    public List<IntegerPointSequence> generate() {
        for (int i = 0; i < ATTEMPTS; i++) {
            try {
                return tryToGenerate();
            } catch (MeanChildFunctionGenerationException e) {
                log.error("Failed to generate child functions, retrying...");
            }
        }

        throw new InvalidMeanFunctionException();
    }

    private List<IntegerPointSequence> tryToGenerate() {
        val functions = initFunctions();

        for (int t = time - 1; t >= 0; t--) {
            val mean = meanFunction.getPoint(t).getValue();
            val delta = (int) Math.round(t0 / (double) (t + 1));

            val generatedValues = generateFunctionValues(
                    getLowerBounds(t, delta, mean),
                    getUpperBounds(t, functions, delta, mean),
                    mean
            );

            for (int i = 0; i < functionsCount; i++) {
                functions.get(i).getValue().set(t, new IntegerPoint(t, generatedValues.get(i)));
            }
        }

        return functions;
    }

    private List<IntegerPointSequence> initFunctions() {
        return new ArrayList<>() {{
            for (int i = 0; i < functionsCount; i++) {
                val value = IntStream.range(0, time)
                        .mapToObj(t -> new IntegerPoint(t, 0))
                        .collect(Collectors.toList());

                add(new IntegerPointSequence(value));
            }
        }};
    }

    private List<Integer> getLowerBounds(Integer time, Integer delta, Integer meanTemp) {
        val minAllowed = lowerBound.getPoint(time).getValue();

        return IntStream.range(0, functionsCount)
                .mapToObj(i -> Math.max(minAllowed, meanTemp - delta))
                .collect(Collectors.toList());
    }

    private List<Integer> getUpperBounds(Integer iteration,
                                         List<IntegerPointSequence> functionsValues,
                                         Integer delta, Integer mean) {

        val maxAllowed = upperBound.getPoint(iteration).getValue();

        return IntStream.range(0, functionsCount)
                .mapToObj(i -> {
                    if (iteration.equals(time - 1)) {
                        return maxAllowed;
                    } else {
                        val nextValue = functionsValues.get(i).getPoint(iteration + 1).getValue();
                        return Math.min(Math.min(maxAllowed, mean + delta), nextValue);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Integer> generateFunctionValues(List<Integer> lowerBounds, List<Integer> upperBounds, Integer mean) {
        val temperatures = new ArrayList<Integer>() {{
            IntStream.range(0, functionsCount).forEach(i ->
                    add(generateValueInInterval(lowerBounds.get(i), upperBounds.get(i))));
        }};

        if (getDifference(mean, temperatures) != 0) {
            adjustTemperatures(temperatures, mean, lowerBounds, upperBounds);
        }

        return temperatures;
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
                                              List<Integer> upperBounds, List<Integer> lowerBounds) {

        return IntStream.range(0, functionsCount)
                .filter(i -> {
                    val functionValue = functionsValues.get(i);

                    if (difference > 0) {
                        return !functionValue.equals(lowerBounds.get(i));
                    } else {
                        return !functionValue.equals(upperBounds.get(i));
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
