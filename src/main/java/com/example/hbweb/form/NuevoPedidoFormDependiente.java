package com.example.hbweb.form;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class NuevoPedidoFormDependiente {
	
    private List<String> idProductosPares;
	
    private List<String> idProductosImpares;

    @NotEmpty(message = "La lista de cantidades de productos no puede estar vacía")
    private List<String> cantidadProductos;

    @NotBlank(message = "El campo cambio no puede estar vacío")
    private String cambio;

    @NotBlank(message = "El campo total no puede estar vacío")
    private String total;

    @NotBlank(message = "El campo importe pagado no puede estar vacío")
    private String importePagado;
    
    public NuevoPedidoFormDependiente(List<String> idProductosPares, List<String> idProductosImpares, List<String> cantidadProductos, String cambio, String total, String importePagado) {
        this.idProductosPares = idProductosPares;
        this.idProductosImpares = idProductosImpares;
        this.cantidadProductos = cantidadProductos;
        this.cambio = cambio;
        this.total = total;
        this.importePagado = importePagado;
    }


    public NuevoPedidoFormDependiente() {
    }

    public List<String> getIdProductosPares() {
        return idProductosPares;
    }

    public void setIdProductosPares(List<String> idProductosPares) {
        this.idProductosPares = idProductosPares;
    }

    public List<String> getIdProductosImpares() {
        return idProductosImpares;
    }

    public void setIdProductosImpares(List<String> idProductosImpares) {
        this.idProductosImpares = idProductosImpares;
    }

    public List<String> getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(List<String> cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }
    

    public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public String getImportePagado() {
		return importePagado;
	}


	public void setImportePagado(String importePagado) {
		this.importePagado = importePagado;
	}


	@Override
    public String toString() {
        return "NuevoPedidoForm [idProductosPares=" + idProductosPares + ", idProductosImpares=" + idProductosImpares
                + ", cantidadProductos=" + cantidadProductos + ", cambio=" + cambio + "]";
    }

}
