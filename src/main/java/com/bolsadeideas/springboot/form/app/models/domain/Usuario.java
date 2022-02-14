package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;

import javax.validation.constraints.Email;
//import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

public class Usuario {

	// Serán validados desde la clase personalizada
	// @Pattern(regexp = "[\\d]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}") //Ejmpl.
	// formato: 23.456.789-M
	@IdentificadorRegex
	private String identificador;

	// @NotEmpty(message = "El nombre no puede ser vacío¡¡") //Para las validaciones
	// en el formulario form.html
	private String nombre;

	// @NotEmpty
	@Requerido
	private String apellido;

	@NotBlank
	@Size(min = 3, max = 8)
	private String username;

	@NotEmpty
	private String password;

	// @NotEmpty //Para tipos String
	@Requerido
	@Email(message = "Correo con formato incorrecto¡¡")
	private String email;

	@NotNull // Para objetos que no sean String. Si es tipo primitivo (int) quitamos este
				// NotNull y solo validamos con Min y Max
	@Min(5)
	@Max(5000)
	private Integer cuenta;

	@NotNull
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past //Valida que sea una fecha pasada a la actual
	//@Future //valida que sea una fecha futura
	private Date fechaNacimiento;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [identificador=");
		builder.append(identificador);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", apellido=");
		builder.append(apellido);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", cuenta=");
		builder.append(cuenta);
		builder.append(", fechaNacimiento=");
		builder.append(fechaNacimiento);
		builder.append("]");
		return builder.toString();
	}

}
