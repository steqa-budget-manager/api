package ru.steqa.api.scheme.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "invalid date format, should be yyyy-MM-dd";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
