package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

public class ThermocoupleCountValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val samples = properties.getSamples();

        for (int i = 0; i < samples.size(); i++) {
            val sample = samples.get(i);
            val thermocoupleCount = sample.getThermocoupleCount();

            if (thermocoupleCount <= 0) {
                return invalid("Thermocouple count is lower that 0 in sample " + (i + 1));
            }
        }

        return valid();
    }

}
