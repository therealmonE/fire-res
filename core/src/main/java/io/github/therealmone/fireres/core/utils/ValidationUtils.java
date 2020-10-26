package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.Point;
import lombok.val;

import java.util.List;

public class ValidationUtils {

    public static boolean constantlyGrowing(List<Point> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            val point = points.get(i);
            val nextPoint = points.get(i + 1);

            if (nextPoint.getTemperature() <= point.getTemperature()) {
                return false;
            }
        }

        return true;
    }

}
