package io.github.therealmone.fireres.core.generator.strategy;

import io.github.therealmone.fireres.core.model.IntegerPointSequence;

import java.util.Iterator;
import java.util.List;

public interface ChildFunctionGeneratorStrategy {

    Iterator<Integer> getTimeIterator(Integer time);

    List<Integer> resolveLowerBounds(IntegerPointSequence lowerBound, Integer time, Integer t0,
                                     List<IntegerPointSequence> functions, Integer meanTemperature);

    List<Integer> resolveUpperBounds(IntegerPointSequence upperBound, Integer time, Integer t0,
                                     List<IntegerPointSequence> functions, Integer meanTemperature);

    Integer resolvePreviousTime(Integer time);

}
