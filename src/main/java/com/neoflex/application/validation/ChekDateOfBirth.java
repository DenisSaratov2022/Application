package com.neoflex.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChekDateOfBirthValidator.class)
public @interface ChekDateOfBirth {

    String message() default "birthdate must be at least 18 years old from today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
