package com.bolsadeideas.springboot.form.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequeridoValidador implements ConstraintValidator<Requerido, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.trim().length() > 0;
	}

}
