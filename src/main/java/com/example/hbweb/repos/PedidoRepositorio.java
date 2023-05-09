package com.example.hbweb.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.hbweb.model.Pedido;

public interface PedidoRepositorio extends CrudRepository<Pedido, Integer> {
	
	@Query("SELECT SUM(p.importeTotal) FROM Pedido p WHERE p.cliente.id = :clienteId GROUP BY p.cliente.id")
    Double TotalImporteByClienteIdQuerry(@Param("clienteId") int clienteId);

	@Query("SELECT p.cliente.id, SUM(p.cantidad), SUM(p.importeTotal) FROM Pedido p GROUP BY p.cliente.id")
	List<Object[]> PedidosByClienteQuerry();
	
	@Query("SELECT p.nombre, pp.cantidad, p.precio, pp.importeTotal FROM Producto p INNER JOIN p.listaPedido pp INNER JOIN pp.cliente c WHERE c.id = :clienteId")
	List<Object[]> ListaDetalleByClienteQuerry(@Param("clienteId") int clienteId);
	
}
