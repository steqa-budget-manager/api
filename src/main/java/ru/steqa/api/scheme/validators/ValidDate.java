package ru.steqa.api.scheme.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "invalid date format, should be yyyy-MM-dd'T'HH:mm:ssZ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
