package com.example.hbweb.form;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class NuevoPedidoFormDependiente {

    private List<String> idProductosPares;
    private List<String> idProductosImpares;
    private List<String> cantidadProductos;
    private String cambio;

    public NuevoPedidoFormDependiente(List<String> idProductosPares, List<String> idProductosImpares, List<String> cantidadProductos, String cambio) {
        this.idProductosPares = idProductosPares;
        this.idProductosImpares = idProductosImpares;
        this.cantidadProductos = cantidadProductos;
        this.cambio = cambio;
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

    @Override
    public String toString() {
        return "NuevoPedidoForm [idProductosPares=" + idProductosPares + ", idProductosImpares=" + idProductosImpares
                + ", cantidadProductos=" + cantidadProductos + ", cambio=" + cambio + "]";
    }

}
