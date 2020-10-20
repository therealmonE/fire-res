package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;

import java.util.List;

public class MathUtils {

    public static Integer calculatePointsMeanValue(List<Point> function) {
        val sum = function.stream()
                .map(Point::getTemperature)
                .reduce(0, Integer::sum);

        return sum / function.size();
    }

    public static Integer calculateIntsMeanValue(List<Integer> function) {
        val sum = function.stream().reduce(0, Integer::sum);

        return sum / function.size();
    }

}
