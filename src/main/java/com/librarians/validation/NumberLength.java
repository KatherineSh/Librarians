package com.librarians.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { NumberLengthValidator.class })
public @interface NumberLength {

	String message() default "{com.librarians.validation.NumberLengthValidator.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	int min() default 0;

	int max() default 10;
}
