package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import lombok.val;

import java.util.List;

public class MathUtils {

    public static Integer calculatePointsMeanValue(List<TemperaturePoint> function) {
        val sum = function.stream()
                .map(TemperaturePoint::getValue)
                .reduce(0, Integer::sum);

        return (int) Math.round(sum / (double) function.size());
    }

    public static Integer calculateIntsMeanValue(List<Integer> function) {
        val sum = function.stream().reduce(0, Integer::sum);

        return (int) Math.round(sum / (double) function.size());
    }

}
