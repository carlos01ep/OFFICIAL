package com.example.hbweb.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BuscarProducto {

	@NotNull(message = "El campo nombre no puede estar vacio")
	@Size(min=3, max=30, message= "El campo nombre debe tener entre 3 y 30 caracteres")
	private String nombre;
	@NotNull(message = "Debes seleccionar una categoría")
	
	private String categoria;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
}
