package com.example.hbweb.form;

public class RolUsuarioForm {
	
	private String email;
	
	private Integer rol;



	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSelected(Integer value) {
        return rol != null && rol.equals(value);
    }
	
	
}
