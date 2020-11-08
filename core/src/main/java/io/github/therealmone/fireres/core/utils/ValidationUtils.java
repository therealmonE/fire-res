package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import lombok.val;

import java.util.List;

public class ValidationUtils {

    public static boolean constantlyGrowing(List<IntegerPoint> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            val point = points.get(i);
            val nextPoint = points.get(i + 1);

            if (nextPoint.getValue() <= point.getValue()) {
                return false;
            }
        }

        return true;
    }

}
