package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class DecreasingChildFunctionGeneratorStrategy implements ChildFunctionGeneratorStrategy {

    private final Integer t0;
    private final IntegerPointSequence lowerBound;
    private final IntegerPointSequence upperBound;

    @Override
    public Iterator<Integer> getTimeIterator(Integer time) {
        return IntStream.range(0, time)
                .map(i -> time - i - 1)
                .iterator();
    }

    @Override
    public List<Integer> resolveLowerBounds(Integer time, List<IntegerPointSequence> functions, Integer meanTemperature) {
        val minAllowed = lowerBound.getPoint(time).getValue();

        return IntStream.range(0, functions.size())
                .mapToObj(i -> Math.max(minAllowed, meanTemperature - resolveDelta(t0, time)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> resolveUpperBounds(Integer time, List<IntegerPointSequence> functions, Integer meanTemperature) {
        val maxAllowed = upperBound.getPoint(time).getValue();

        return functions.stream()
                .map(function -> {
                    if (!function.getValue().isEmpty()) {
                        val nextValue = function.getPoint(time + 1).getValue();
                        return Math.min(Math.min(maxAllowed, meanTemperature + resolveDelta(t0, time)), nextValue);
                    } else {
                        return maxAllowed;
                    }
                })
                .collect(Collectors.toList());
    }

    public int resolveDelta(Integer t0, Integer time) {
        return (int) Math.round(t0 / (double) (time + 1));
    }

}
