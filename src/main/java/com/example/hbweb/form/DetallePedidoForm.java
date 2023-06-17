package com.example.hbweb.form;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DetallePedidoForm {
	private String estado;
	private String direccion;
	private int id;
	


	//    @NotBlank(message = "El número de tarjeta es obligatorio")
    private String numeroTarjeta;

//    @NotBlank(message = "El titular de la tarjeta es obligatorio")
    private String titularTarjeta;

//    @Pattern(regexp = "\\d{2}/\\d{2}", message = "El formato de la fecha de vencimiento debe ser MM/AA")
    private String fechaVencimiento;

//    @Size(min = 3, max = 4, message = "El código CVV debe tener entre 3 y 4 dígitos")
    private String codigoCVV;
    
    private String delivery;

	public DetallePedidoForm() {
		super();
	}

	public DetallePedidoForm(int id, String numeroTarjeta, String titularTarjeta, 
			String fechaVencimiento, String codigoCVV, String direccion, String delivery, String estado) {
        super();
        this.id = id;
        this.estado = estado;
        this.delivery = delivery;
        this.numeroTarjeta = numeroTarjeta;
        this.titularTarjeta = titularTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoCVV = codigoCVV;
        this.direccion = direccion;
    }
	
	public DetallePedidoForm(int id,  String estado) {
        super();
        this.id = id;
        this.estado = estado;
    }

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getTitularTarjeta() {
		return titularTarjeta;
	}

	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getCodigoCVV() {
		return codigoCVV;
	}

	public void setCodigoCVV(String codigoCVV) {
		this.codigoCVV = codigoCVV;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String dirección) {
		this.direccion = dirección;
	}

	@Override
	public String toString() {
		return "PagoPedidoForm [numeroTarjeta=" + numeroTarjeta + ", titularTarjeta=" + titularTarjeta
				+ ", fechaVencimiento=" + fechaVencimiento + ", codigoCVV=" + codigoCVV + "]";
	}

}