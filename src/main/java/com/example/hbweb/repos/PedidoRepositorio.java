package com.example.hbweb.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.hbweb.model.Pedido;

public interface PedidoRepositorio extends CrudRepository<Pedido, Integer> {
	
	@Query("SELECT SUM(p.importeTotal) FROM Pedido p WHERE p.pedidoDetalle.id = :pedidoDetalleId GROUP BY p.pedidoDetalle.id")
    Double totalImporteByPedidoDetalleIdQuerry(@Param("pedidoDetalleId") int pedidoDetalleId);

	@Query("SELECT p.pedidoDetalle.id, SUM(p.cantidad), SUM(p.importeTotal) FROM Pedido p GROUP BY p.pedidoDetalle.id")
	List<Object[]> pedidosByPedidoDetalleQuerry();
	
	@Query("SELECT p.nombre, pp.cantidad, p.precio, pp.importeTotal FROM Producto p INNER JOIN p.listaPedido pp INNER JOIN pp.pedidoDetalle pd WHERE pd.id = :pedidoId")
	List<Object[]> listaDetalleByPedidoDetalleQuerry(@Param("pedidoId") int pedidoId);

	
}
