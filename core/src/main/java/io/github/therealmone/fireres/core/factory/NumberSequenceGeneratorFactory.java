package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.generator.FurnaceTempGenerator;
import io.github.therealmone.fireres.core.generator.NumberSequenceGenerator;
import io.github.therealmone.fireres.core.generator.StandardTempGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class NumberSequenceGeneratorFactory {

    private final GenerationProperties generationProperties;
    //todo custom resolver
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public StandardTempGenerator standardTempGenerator() {
        val generator  = new StandardTempGenerator(
                generationProperties.getT0(),
                generationProperties.getTime());

        validate(generator);
        return generator;
    }

    public FurnaceTempGenerator furnaceTempGenerator(List<Integer> standardTemp) {
        val generator = new FurnaceTempGenerator(generationProperties.getT0(), standardTemp);

        validate(generator);
        return generator;
    }

    private void validate(NumberSequenceGenerator generator) {
        val violations = validator.validate(generator);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> log.error("Constraint violation on generator {}: {}",
                    generator.getClass().getSimpleName(), violation.getMessage()));

            throw new ValidationException("Generator is invalid");
        }
    }

}
