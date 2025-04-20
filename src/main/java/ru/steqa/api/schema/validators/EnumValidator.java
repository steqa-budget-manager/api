package ru.steqa.api.schema.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private List<String> allowedValues;
    private boolean ignoreCase;
    private String message;

    @Override
    public void initialize(ValidEnum annotation) {
        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        ignoreCase = annotation.ignoreCase();
        allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .toList();
        message = annotation.message()
                .replace("{allowedValues}", String.join(", ", allowedValues));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        boolean valid = allowedValues.stream().anyMatch(
                enumVal -> ignoreCase ? enumVal.equalsIgnoreCase(value) : enumVal.equals(value)
        );

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return valid;
    }
}
