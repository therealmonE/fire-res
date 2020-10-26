package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

public class MinCoefficientsValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val minCoefficients = properties.getTemperature().getMinAllowedTempCoefficients();

        for (int i = 0; i < minCoefficients.size(); i++) {
            val minCoefficient = minCoefficients.get(i);

            if (minCoefficient.getValue() <= 0 || minCoefficient.getValue() >= 1) {
                return invalid("Min temperature coefficient " + (i + 1) + " is out of bounds (0 ; 1)");
            }
        }

        return valid();
    }

}
