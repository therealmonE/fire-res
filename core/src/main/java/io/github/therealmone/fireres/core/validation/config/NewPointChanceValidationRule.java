package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

public class NewPointChanceValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val samples = properties.getSamples();

        for (int i = 0; i < samples.size(); i++) {
            val sample = samples.get(i);
            val newPointChance = sample.getRandomPoints().getNewPointChance();

            if (newPointChance < 0 || newPointChance > 1) {
                return invalid("New point chance is out of bounds [0 ; 1] in sample " + (i + 1));
            }
        }

        return valid();
    }

}
