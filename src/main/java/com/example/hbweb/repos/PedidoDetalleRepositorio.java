package com.example.hbweb.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.hbweb.model.PedidoDetalle;

public interface PedidoDetalleRepositorio extends CrudRepository<PedidoDetalle, Integer>{
	PedidoDetalle findById(int id);
}
