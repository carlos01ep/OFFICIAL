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
@Table(name = "pedidodetalle")
public class PedidoDetalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String horaPrevistaDeLlegada;
	private String direccion;
	private String estado;
	private Double total;
	private Double importePagado;
	private Double cambio;
	private Double descuento;
	@ManyToOne
	@JoinColumn(name = "usuario_pedido")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "usuario_empleado")
	private Usuario usuarioEmpleado;
	
	@ManyToOne
	@JoinColumn(name = "usuario_delivery")
	private Usuario usuarioDelivery;
	
	
	@OneToMany(mappedBy = "pedidoDetalle")
    private List<Pedido> listaPedido;



	public PedidoDetalle() {
		super();

	}

	public PedidoDetalle(Usuario usuario, Double total) {
		super();
		this.horaPrevistaDeLlegada = null;
		this.direccion = null;
		this.usuarioEmpleado = null;
		this.usuarioDelivery = null;
		this.usuario = usuario;
		this.nombre = "sin definir";
		this.estado = "carrado";
		this.descuento = 0.0;
		this.cambio = 0.0;
		this.total = total;
		this.importePagado = 0.0;
		this.listaPedido = new ArrayList<>();
	}
	
	

	
	
	public Usuario getUsuarioEmpleado() {
		return usuarioEmpleado;
	}

	public void setUsuarioEmpleado(Usuario usuarioEmpleado) {
		this.usuarioEmpleado = usuarioEmpleado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioDelivery() {
		return usuarioDelivery;
	}

	public void setUsuarioDelivery(Usuario usuarioDelivery) {
		this.usuarioDelivery = usuarioDelivery;
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
		return "PedidoDetalle [id=" + id + ", nombre=" + nombre + ", estado=" + estado + ", total=" + total
				+ ", importePagado=" + importePagado + ", cambio=" + cambio + ", descuento=" + descuento + ", usuario="
				+ usuario + ", usuarioDelivery=" + usuarioDelivery + ", listaPedido=" + listaPedido + "]";
	}

	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public Double getImportePagado() {
		return importePagado;
	}



	public void setImportePagado(Double importePagado) {
		this.importePagado = importePagado;
	}



	public Double getCambio() {
		return cambio;
	}



	public void setCambio(Double cambio) {
		this.cambio = cambio;
	}



	public Double getDescuento() {
		return descuento;
	}



	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public String getHoraPrevistaDeLlegada() {
		return horaPrevistaDeLlegada;
	}

	public void setHoraPrevistaDeLlegada(String horaPrevistaDeLlegada) {
		this.horaPrevistaDeLlegada = horaPrevistaDeLlegada;
	}


	
	
	
}
