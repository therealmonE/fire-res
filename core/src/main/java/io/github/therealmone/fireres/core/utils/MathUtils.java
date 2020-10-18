package io.github.therealmone.fireres.core.utils;

import lombok.val;

import java.util.List;

public class MathUtils {

    public static Integer calculateMeanValue(List<Integer> temperatures) {
        val sum = temperatures.stream().reduce(0, Integer::sum);

        return sum / temperatures.size();
    }

}
