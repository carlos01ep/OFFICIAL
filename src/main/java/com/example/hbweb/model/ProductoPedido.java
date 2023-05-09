package com.example.hbweb.model;

import jakarta.validation.constraints.Positive;

public class ProductoPedido {
	private Integer id;
	 @Positive
	private Integer cantidad;
	
	
	public ProductoPedido() {
		super();
	}
	public ProductoPedido(Integer id, Integer cantidad) {
		super();
		this.id = id;
		this.cantidad = cantidad;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
