package com.librarians.validation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy= com.librarians.validation.YearValidator.class)
public @interface Year {

	String message() default "{com.librarians.validation.YearValidator.message}"; 
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
	short min() default 1900;	
}
