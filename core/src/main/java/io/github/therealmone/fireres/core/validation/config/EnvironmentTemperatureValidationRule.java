package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

public class EnvironmentTemperatureValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val environmentTemperature = properties.getTemperature().getEnvironmentTemperature();

        return environmentTemperature > 0
                ? valid()
                : invalid("Environment temperature is lower than 0");
    }

}
