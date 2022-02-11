package com.bolsadeideas.springboot.form.app.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

@Controller
@SessionAttributes("user")//Permite guardar por sesión el objeto "user". La información que no se use en el formulario no se perderá.
public class FormController {

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setIdentificador("23.456.789-M");//Este es un atributo que no está en el formulario, pero es parte del objeto. Este atributo no se perdrá
		usuario.setNombre("Martín");
		usuario.setApellido("Díaz");
		
		model.addAttribute("titulo", "Formulario usuarios");
		model.addAttribute("user", usuario);
		return "form";
	}

	/**
	 * El @ModelAttribute, cambia el nombre del objeto usuario por user para
	 * cuando se envie a la vista, esta pueda ser accedido con user
	 */
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		
		model.addAttribute("titulo", "Resultado form");
		
		if(result.hasErrors()) {
			return "form";
		}
				
		model.addAttribute("usuario", usuario);
		
		//Elimina el objeto "user" de la sesión (declarado en @SessionAttributes)
		//Si usamos el @SessionAttributes es importante agregar ese método
		status.setComplete(); 
		return "resultado";
	}

}
