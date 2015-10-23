package com.librarians.validation;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<Year, Short> {

	private Short min;
	private final int FOUR_DIGIT_VALUE = 4;
	
	@Override
	public void initialize(Year constraintAnnotation) {
		this.min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(Short value, ConstraintValidatorContext context) {		
		if(value == null) {
			return true;
		}
		//check that it's 4 digits value
		if(String.valueOf(value).length() == FOUR_DIGIT_VALUE){
			
			//check that it's not in future
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			if( value >= min && value <= currentYear) {
				return true;
			}
		}
		return false;
	}

}
