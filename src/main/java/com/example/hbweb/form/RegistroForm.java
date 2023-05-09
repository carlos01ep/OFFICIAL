package com.example.hbweb.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class RegistroForm {
	private String idEmail = "";
	@NotNull
	@Email(message="Email no válido")
	private String email;
	//@NotNull
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", 
    message="La contraseña debe tener al menos una letra mayúscula, una letra minúscula, un número y una longitud mínima de 8 caracteres")
	private String password;
	@NotNull
	@Size(min=3, max=30, message="El campo nombre debe tener entre 3 y 30 caracteres")
	private String nombre;
	@NotNull
	@Size(min=3, max=30, message="El campo apellidos debe tener entre 3 y 30 caracteres")
	private String apellidos;
	@NotNull
	@Pattern(regexp="^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$", 
    message="El DNI debe tener 8 dígitos seguido de una letra (mayúscula o minúscula)")
	private String dni;
	@NotNull
	@Pattern(regexp="^[0-9]{9}$", message="El número de teléfono debe tener 9 dígitos")
	private String telefono;
	
	private Integer rol;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public Integer getRol() {
		return rol;
	}
	public void setRol(Integer rol) {
		this.rol = rol;
	}

}
