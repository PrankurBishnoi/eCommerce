package com.prankur.eCommerce.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = MatchingpasswordConstraintValidator.class)
@Target({TYPE,FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface MatchingPassword
{
    String message() default "Passwords Don't Match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
