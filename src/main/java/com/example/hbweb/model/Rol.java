package com.example.hbweb.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tipo;
	private String descripcion;
	
	 @OneToMany(mappedBy = "rol")
	    private List<Usuario> listaUsuario;

	 
	 
	 
	public Rol() {
		super();
	}

	public Rol(Integer tipo, String descripcion) {
		super();
		this.tipo = tipo;
		this.descripcion = descripcion;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	 
	 
}
