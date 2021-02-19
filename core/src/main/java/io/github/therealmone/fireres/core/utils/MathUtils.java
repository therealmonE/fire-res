package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;

import java.util.List;

public class MathUtils {

    public static Double calculatePointsMeanValue(List<? extends Point<?>> function) {
        val sum = function.stream()
                .map(point -> point.getValue().doubleValue())
                .reduce(0d, Double::sum);

        return sum / (double) function.size();
    }

    public static Integer calculateIntsMeanValue(List<Integer> function) {
        val sum = function.stream().reduce(0, Integer::sum);

        return (int) Math.round(sum / (double) function.size());
    }
}
