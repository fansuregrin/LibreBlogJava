package org.fansuregrin.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.fansuregrin.validation.NotBlankIfNotNullValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = NotBlankIfNotNullValidator.class)
public @interface NotBlankIfNotNull {
    String message() default "cannot be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
