package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

public class MaxCoefficientsValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val maxCoefficients = properties.getTemperature().getMaxAllowedTempCoefficients();

        for (int i = 0; i < maxCoefficients.size(); i++) {
            val maxCoefficient = maxCoefficients.get(i);

            if (maxCoefficient.getValue() <= 1 || maxCoefficient.getValue() >= 2) {
                return invalid("Max temperature coefficient " + (i + 1) + " is out of bounds (1 ; 2)");
            }
        }

        return valid();
    }

}
