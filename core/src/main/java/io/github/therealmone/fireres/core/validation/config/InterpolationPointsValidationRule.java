package io.github.therealmone.fireres.core.validation.config;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.validation.AbstractValidationRule;
import io.github.therealmone.fireres.core.validation.ValidationResult;
import lombok.val;

import static io.github.therealmone.fireres.core.utils.ValidationUtils.constantlyGrowing;

public class InterpolationPointsValidationRule extends AbstractValidationRule<GenerationProperties> {

    @Override
    public ValidationResult validate(GenerationProperties properties) {
        val samples = properties.getSamples();

        for (int i = 0; i < samples.size(); i++) {
            val sample = samples.get(i);
            val interpolationPoints = sample.getInterpolationPoints();

            if (!constantlyGrowing(interpolationPoints)) {
                return invalid("Interpolation points not constantly growing in sample " + (i + 1));
            }
        }

        return valid();
    }

}
