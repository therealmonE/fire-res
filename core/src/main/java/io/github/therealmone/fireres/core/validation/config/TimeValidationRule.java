package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

public class TimeValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val time = properties.getTime();

        return time > 0
                ? valid()
                : invalid("Time is lower than 0");
    }

}
