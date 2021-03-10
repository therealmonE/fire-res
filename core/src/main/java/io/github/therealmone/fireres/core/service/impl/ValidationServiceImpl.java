package io.github.therealmone.fireres.core.service.impl;

import io.github.therealmone.fireres.core.model.FunctionsGenerationParams;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.service.ValidationService;
import lombok.val;

import java.util.List;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isFunctionGenerationParamsValid(FunctionsGenerationParams params) {
        return isMeanFormInBounds(params)
                && isFormsConstantlyGrowing(params);
    }

    private boolean isMeanFormInBounds(FunctionsGenerationParams generationParameters) {
        val lowerBound = generationParameters.getMeanBounds().getLowerBound();
        val upperBound = generationParameters.getMeanBounds().getUpperBound();
        val meanForm = generationParameters.getMeanFunctionForm();

        for (Point<?> interpolationPoint : meanForm.getInterpolationPoints()) {
            val min = lowerBound.getPoint(interpolationPoint.getTime()).getValue();
            val max = upperBound.getPoint(interpolationPoint.getTime()).getValue();
            val pointValue = interpolationPoint.getIntValue();

            if (pointValue < min || pointValue > max) {
                return false;
            }
        }

        return true;
    }

    private boolean isFormsConstantlyGrowing(FunctionsGenerationParams params) {
        return isPointsConstantlyGrowing(params.getMeanFunctionForm().getInterpolationPoints());
    }

    private boolean isPointsConstantlyGrowing(List<? extends Point<?>> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            val point = points.get(i);
            val nextPoint = points.get(i + 1);

            if (point.getValue().doubleValue() > nextPoint.getValue().doubleValue()) {
                return false;
            }
        }

        return true;
    }

}
