package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.exception.MeanChildFunctionGenerationException;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculateIntsMeanValue;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Slf4j
public class MeanChildFunctionsGenerator implements MultiplePointSequencesGenerator<IntegerPointSequence> {


    private final Integer time;
    private final IntegerPointSequence meanFunction;
    private final Integer functionsCount;
    private final ChildFunctionGeneratorStrategy strategy;

    @Override
    public List<IntegerPointSequence> generate() {
        val functions = initFunctions();

        val timeIterator = strategy.getTimeIterator(time);

        while (timeIterator.hasNext()) {
            val t = timeIterator.next();
            val mean = meanFunction.getPoint(t).getValue();

            val generatedValues = generateFunctionValues(
                    strategy.resolveLowerBounds(t, functions, mean),
                    strategy.resolveUpperBounds(t, functions, mean),
                    mean
            );

            for (int i = 0; i < functionsCount; i++) {
                functions.get(i).getValue().add(new IntegerPoint(t, generatedValues.get(i)));
            }
        }

        functions.forEach(f -> f.getValue().sort(Comparator.comparing(Point::getTime)));
        return functions;

    }

    private List<IntegerPointSequence> initFunctions() {
        return new ArrayList<>() {{
            for (int i = 0; i < functionsCount; i++) {
                add(new IntegerPointSequence(new ArrayList<>()));
            }
        }};
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
