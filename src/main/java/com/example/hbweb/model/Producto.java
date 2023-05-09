package com.example.hbweb.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nombre;
	private String categoria;
	private String ingredientes;
	private String image;
	private Double precio;
	private boolean stock;
	// realcion
	@ManyToOne
	@JoinColumn(name = "usuario_email")
	private Usuario usuario;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "pedido_producto", 
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "pedido_id")
    )
    private List<Pedido> listaPedido;

	// constructores
	public Producto() {
		super();
	}

	public Producto(String nombre, String categoria, String ingredientes, Double precio, boolean stock,
			Usuario usuario) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.ingredientes = ingredientes;
		this.image = "undefined.png";
		this.precio = precio;
		this.stock = stock;
		this.usuario = usuario;
		this.listaPedido = new ArrayList<>();
	}

	// getters and setters

	public Producto(String nombre, String categoria, String ingredientes, String image, Double precio, boolean stock,
			Usuario usuario) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.ingredientes = ingredientes;
		this.image = image;
		this.precio = precio;
		this.stock = stock;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}


	
	

}
