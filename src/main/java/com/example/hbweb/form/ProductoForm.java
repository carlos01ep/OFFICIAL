package com.example.hbweb.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductoForm {
	private String id;
	
	@NotNull(message = "El campo nombre no puede estar vacio")
	@Size(min=3, max=30, message= "El campo nombre debe tener entre 3 y 30 caracteres")
	private String nombre;
	@NotNull(message = "Debes seleccionar una categoría")
	
	private String categoria;
	
	private String ingredientes;
	@NotNull(message = "Debes seleccionar una imagen")
	
	private String image;
	@NotNull(message = "Debes asignarle un precio al producto")
	@DecimalMax(value = "99.99", message = "El precio debe ser inferior a 100.00 ")
	@DecimalMin(value = "0.00", message = "El precio debe ser igual o superior a 0.00 ")
	private Double precio;
	
	//@AssertTrue esta validacion obliga que el check box este marcado, podría ser util en otro contexto como en "aceptar los terminos" de registro
	private boolean disponible;
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
	public String getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ProductoForm [nombre=" + nombre + ", categoria=" + categoria + ", ingredientes=" + ingredientes
				+ ", image=" + image + ", precio=" + precio + ", disponible=" + disponible + "]";
	}
	
	
}
