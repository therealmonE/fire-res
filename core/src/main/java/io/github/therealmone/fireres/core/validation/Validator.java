package io.github.therealmone.fireres.core.validation;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.exception.ValidationException;
import io.github.therealmone.fireres.core.validation.config.EnvironmentTemperatureValidationRule;
import io.github.therealmone.fireres.core.validation.config.InterpolationPointsValidationRule;
import io.github.therealmone.fireres.core.validation.config.MaxCoefficientsValidationRule;
import io.github.therealmone.fireres.core.validation.config.MinCoefficientsValidationRule;
import io.github.therealmone.fireres.core.validation.config.NewPointChanceValidationRule;
import io.github.therealmone.fireres.core.validation.config.ThermocoupleCountValidationRule;
import io.github.therealmone.fireres.core.validation.config.TimeValidationRule;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.core.validation.ValidationStatus.INVALID;

public class Validator {

    private static final List<ValidationRule<GenerationProperties>> PROPERTIES_RULES = List.of(
            new TimeValidationRule(),
            new EnvironmentTemperatureValidationRule(),
            new MinCoefficientsValidationRule(),
            new MaxCoefficientsValidationRule(),
            new InterpolationPointsValidationRule(),
            new NewPointChanceValidationRule(),
            new ThermocoupleCountValidationRule()
    );

    public static void validateGenerationProperties(GenerationProperties generationProperties) {
        applyRules(PROPERTIES_RULES, generationProperties);
    }

    private static <T> void applyRules(List<ValidationRule<T>> rules, T object) {
        val errors = rules.stream()
                .map(rule -> rule.validate(object))
                .filter(result -> result.getStatus() == INVALID)
                .map(ValidationResult::getErrorMessage)
                .toArray(String[]::new);

        if (!(errors.length == 0)) {
            throw new ValidationException(errors);
        }
    }

}
