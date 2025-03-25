package org.fansuregrin.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fansuregrin.annotation.NotBlankIfNotNull;

public class NotBlankIfNotNullValidator
    implements ConstraintValidator<NotBlankIfNotNull, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        int n;
        if ((n = value.length()) <= 0) return false;
        for (int i=0; i<n; ++i) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }
        return true;
    }

}
