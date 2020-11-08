package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import org.apache.commons.math3.util.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionUtils {

    public static List<Pair<IntegerPoint, IntegerPoint>> asIntervals(List<IntegerPoint> function) {
        if (function.size() <= 1) {
            throw new IllegalArgumentException();
        }

        return IntStream.range(1, function.size())
                .mapToObj(i -> new Pair<>(function.get(i - 1), function.get(i)))
                .collect(Collectors.toList());
    }

}
