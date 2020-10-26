package io.github.therealmone.fireres.core.exception;

import java.util.Arrays;

public class ValidationException extends RuntimeException {
    public ValidationException(String[] errors) {
        super("Validation errors: " + Arrays.toString(errors));
    }
}
