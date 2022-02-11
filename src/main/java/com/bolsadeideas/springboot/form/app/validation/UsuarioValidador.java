package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

@Component
public class UsuarioValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		//Para verificar que el objeto que estemos pasando(clazz) sea el de tipo Usuario y no otro
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Usuario usuario = (Usuario)target;
		
		//Valida campo "nombre"
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "requerido.user.nombre");//"NotEmpty.user.nombre", está en el archivo de messages.properties
		
		//Verifica patrón del campo "identificador"
		if(!usuario.getIdentificador().matches("[\\d]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {
			errors.rejectValue("identificador", "pattern.user.identificador");//"pattern.user.identificador", está en el archivo de messages.properties
		}
	}

}
