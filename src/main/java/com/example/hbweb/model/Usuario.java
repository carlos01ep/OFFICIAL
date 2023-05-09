package com.example.hbweb.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	private String email;
	private String contraseña;
	private String dni;
	private String nombre;
	private String apellidos;
	private String telefono;
	// Relacion
	@ManyToOne
	@JoinColumn(name = "rol_tipo")
	private Rol rol;
	@OneToMany(mappedBy = "usuario")
	private List<Producto> listaProductos;
	@OneToMany(mappedBy = "usuario")
	private List<Pedido> listaPedido;

	public Usuario() {
		super();
	}

	public Usuario(String email, String contraseña, String dni, String nombre, String apellidos, String telefono,
			Rol rol) {
		super();
		this.email = email;
		this.contraseña = contraseña;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", contraseña=" + contraseña + ", dni=" + dni + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", telefono=" + telefono + ", rol=" + rol + "]";
	}

}
