package com.example.hbweb.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
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
@Table(name = "pedido")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String establecimiento;
	@ManyToOne
	@JoinColumn(name = "usuario_pedido")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "cliente_pedido")
	private Cliente cliente;
	private String estado;
	private Date fechaPedido;
	private Date fechaEntrega;
	private boolean paraLlevar;

	@ManyToMany(mappedBy = "listaPedido", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Producto> listaProducto;
	private Double importeTotal;
	private Integer cantidad;

	
	
	public Pedido() {
		super();
	}

	public Pedido(Usuario usuario, Cliente cliente, Integer cantdidad) {
		super();
		this.establecimiento = "Hubo Burger Online";
		this.estado = "Pendiente";
		this.fechaPedido = new Date();
		this.fechaEntrega = sumarRestarMinutosFecha(this.fechaPedido, 10);
		this.paraLlevar = true;
		this.cantidad = cantdidad;
		this.importeTotal = 0.0;
		this.usuario = usuario;
		this.cliente = cliente;
		this.listaProducto = new ArrayList<>();

	}

	public Date sumarRestarMinutosFecha(Date fecha, int minutos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.MINUTE, minutos); // número de minutos a añadir o restar en caso de minutos<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos minutos añadidos
	}

	@PostConstruct
	private void init() {
		listaProducto = new ArrayList<>();
	}

	public void sumarPrecioImporteTotal(Double precio) {
		this.importeTotal = +precio;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public boolean isParaLlevar() {
		return paraLlevar;
	}

	public void setParaLlevar(boolean paraLlevar) {
		this.paraLlevar = paraLlevar;
	}

	public List<Producto> getListaProductos() {
		return listaProducto;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProducto = listaProductos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void addProducto(Producto producto) {
		if (this.listaProducto == null) {
			this.listaProducto = new ArrayList<>();
		}
		this.listaProducto.add(producto);
	}

}
