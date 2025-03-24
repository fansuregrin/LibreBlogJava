package org.fansuregrin.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.fansuregrin.validation.UsernameValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {
    String message() default "invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
