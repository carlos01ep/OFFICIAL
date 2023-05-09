package com.example.hbweb.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import java.util.List;
import com.example.hbweb.model.ProductoJAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productos")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductosJAXB {
	@XmlElement(name = "producto")
	    private List<ProductoJAXB> productos = null;

	public ProductosJAXB(List<ProductoJAXB> productos) {
		super();
		this.productos = productos;
	}

	public ProductosJAXB() {
		super();
	}

	public List<ProductoJAXB> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoJAXB> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "ProductosJAXB [productos=" + productos + "]";
	}

	 
	 
	 
	 
}
