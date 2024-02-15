package com.microcompany.accountsservice.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AccountOwner.Validator.class})
public @interface AccountOwner {

    public class Validator implements ConstraintValidator<AccountOwner, String> {
        @Override
        public void initialize(final AccountOwner name) {
        }

        @Override
        public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
            String cleanName = s.trim();
            return (cleanName.length() >= 3 && cleanName.length() <= 20);
        }
    }
}
