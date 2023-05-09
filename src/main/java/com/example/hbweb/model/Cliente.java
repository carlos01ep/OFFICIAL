package com.example.hbweb.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;

	
	
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> listaPedido;



	public Cliente() {
		super();
		this.nombre = "sin definir";
		this.listaPedido = new ArrayList<>();
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



	public List<Pedido> getListaPedido() {
		return listaPedido;
	}



	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}



	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", listaPedido=" + listaPedido + "]";
	}
	
	
	
}
