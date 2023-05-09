package com.example.hbweb.form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public class LoginForm {
	@NotNull(message = "El campo email no puede estar vacio")
	@Email(message= "El campo email debe tener el formato 'email@email.em'")
	private String email;
	@NotNull(message = "El campo password no puede estar vacio")
	private String password;
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
	
	
}
