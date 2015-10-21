package com.librarians.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberLengthValidator implements ConstraintValidator<NumberLength, Number>{

	private int min;
	
	private int max;
	
	@Override
	public void initialize(NumberLength constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
				
		if(value == null) {
			return true;
		}
		int digitCount = String.valueOf(value).length();
		if(digitCount <= max && digitCount >= min){
			return true;
		}
		return false;
	}
}
