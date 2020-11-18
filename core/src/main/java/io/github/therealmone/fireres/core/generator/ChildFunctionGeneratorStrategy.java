package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.IntegerPointSequence;

import java.util.Iterator;
import java.util.List;

public interface ChildFunctionGeneratorStrategy {

    Iterator<Integer> getTimeIterator(Integer time);

    List<Integer> resolveLowerBounds(Integer time, List<IntegerPointSequence> functions, Integer meanTemperature);

    List<Integer> resolveUpperBounds(Integer time, List<IntegerPointSequence> functions, Integer meanTemperature);

}
