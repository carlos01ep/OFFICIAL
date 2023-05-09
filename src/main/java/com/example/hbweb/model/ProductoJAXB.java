package com.example.hbweb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import com.example.hbweb.model.ProductosJAXB;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductoJAXB {

	@XmlElement(name = "nombre")
	private String nombre;
	@XmlElement(name = "categoria")
	private String categoria;
	@XmlElement(name = "ingredientes")
	private String ingredientes;
	@XmlElement(name = "image")
	private String image;
	@XmlElement(name = "precio")
	private Double precio;
	@XmlElement(name = "stock")
	private boolean stock;

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

	public boolean isStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public ProductoJAXB(String nombre, String categoria, String ingredientes, String image, Double precio,
			boolean stock) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.ingredientes = ingredientes;
		this.image = image;
		this.precio = precio;
		this.stock = stock;
	}

	public ProductoJAXB() {
		super();
	}

	@Override
	public String toString() {
		return "ProductoJAXB [nombre=" + nombre + ", categoria=" + categoria + ", ingredientes=" + ingredientes
				+ ", image=" + image + ", precio=" + precio + ", stock=" + stock + "]";
	}

}
