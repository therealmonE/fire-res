package io.github.therealmone.fireres.core.validation;

public interface ValidationRule<T> {

     ValidationResult validate(T object);

}
