package io.github.therealmone.fireres.core.validation;

public abstract class AbstractValidationRule<T> implements ValidationRule<T> {

    protected ValidationResult valid() {
        return ValidationResult.builder()
                .status(ValidationStatus.VALID)
                .build();
    }

    protected ValidationResult invalid(String error) {
        return ValidationResult.builder()
                .status(ValidationStatus.INVALID)
                .errorMessage(error)
                .build();
    }

}
