package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.IPaisService;
import com.bolsadeideas.springboot.form.app.services.IRoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("user") // Permite guardar por sesión el objeto "user". La información que no se use en el formulario no se perderá.
public class FormController {

	@Autowired
	private UsuarioValidador validador;

	@Autowired
	private IPaisService paisService;

	@Autowired
	private PaisPropertyEditor paisEditor;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private RolesEditor roleEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(this.validador);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);// El análisis de la fecha será estricto
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));

		// Solo se aplicará al atributo "nombre" de la clase Usuario, si no se le pone,
		// se aplicará a todos los String
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		// Aplicará al atributo "apellido"
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());

		binder.registerCustomEditor(Pais.class, "pais", this.paisEditor);

		// Por cada rol que se seleccione se aplica este rol editor
		binder.registerCustomEditor(Role.class, "roles", this.roleEditor);
	}

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setIdentificador("23.456.789-M");// Este es un atributo que no está en el formulario, pero es parte del
													// objeto. Este atributo no se perdrá
		usuario.setNombre("Martín");
		usuario.setApellido("Díaz");
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Esto es información secreta");
		usuario.setPais(new Pais(2, "PE", "Perú"));
		usuario.setRoles(
				Arrays.asList(new Role(2, "Usuario", "ROLE_USER"), new Role(3, "Moderador", "ROLE_MODERATOR")));

		model.addAttribute("titulo", "Formulario usuarios");
		model.addAttribute("user", usuario);
		return "form";
	}

	/**
	 * El @ModelAttribute, cambia el nombre del objeto usuario por user para cuando
	 * se envie a la vista, esta pueda ser accedido con user
	 */
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model) {

		// Validamos usando la clase validadora
		// this.validador.validate(usuario, result);//Comentado ya que se trabajó arriba
		// con el @InitBinder, por debajo ya trabaja esto
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Resultado form");
			return "form";
		}

		return "redirect:/ver";
	}

	@GetMapping("/ver")
	public String ver(@SessionAttribute(name = "user", required = false) Usuario usuario, Model model, SessionStatus status) {
		//Si estando en la página /ver, se vuelve actualizar ya el usuario se habrá perdido (por esto status.setComplete())
		//Si no validamos, daría un error. En ese caso lo validamos y redirigimos al formulario
		if(usuario == null) {
			return "redirect:/form";
		}
		
		model.addAttribute("titulo", "Resultado form");
		
		//Esto se podría omitir, ya que por defecto en el resultado.html ya estaría disponible
		//el objeto user ya que es un atributo que está guardado en la sesión, 
		//y se podría acceder desde ese resultado.html con ese mismo objeto user. 
		//En nuestro caso, en el resultado.html estamos accediendo con usuario, por eso estamos
		//volviendo a mandar el atributo usuario con el objeto usuario
		model.addAttribute("usuario", usuario);
		
		// Elimina el objeto "user" de la sesión (declarado en @SessionAttributes)
		// Si usamos el @SessionAttributes es importante agregar ese método
		status.setComplete();
		return "resultado";
	}

	@ModelAttribute("generos")
	public List<String> genero() {
		return Arrays.asList("Hombre", "Mujer");
	}

	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString() {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}

	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap() {
		Map<String, String> roles = new HashMap<>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		return roles;
	}

	@ModelAttribute("listaRoles")
	public List<Role> listaRoles() {
		return this.roleService.listar();
	}

	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises() {
		return this.paisService.listar();
	}

	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("Argentina", "Perú", "Brasil", "Colombia", "España", "México", "EEUU");
	}

	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<>();
		paises.put("PE", "Perú");
		paises.put("ES", "España");
		paises.put("AR", "Argentina");
		paises.put("CL", "Chile");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		return paises;
	}

}
