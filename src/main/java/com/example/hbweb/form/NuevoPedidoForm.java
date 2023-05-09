package com.example.hbweb.form;

import java.util.List;

import com.example.hbweb.model.ProductoPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class NuevoPedidoForm {

	// @Valid (message = "Debes al menos un producto")
	 //@NotNull(message = "Debes al menos un producto")
	private List<String> idProductos;
	private List<String> cantidadProductos;
	
	
	
	
	
	public NuevoPedidoForm(List<String> idProductos, List<String> cantidadProductos) {
		super();
		this.idProductos = idProductos;
		this.cantidadProductos = cantidadProductos;
	}
	
	
	
	
	public NuevoPedidoForm() {
		super();
	}




	public List<String> getIdProductos() {
		return idProductos;
	}
	public void setIdProductos(List<String> idProductos) {
		this.idProductos = idProductos;
	}
	public List<String> getCantidadProductos() {
		return cantidadProductos;
	}
	public void setCantidadProductos(List<String> cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}




	@Override
	public String toString() {
		return "NuevoPedidoForm [idProductos=" + idProductos + ", cantidadProductos=" + cantidadProductos + "]";
	}
	
	
	  
	  
}
