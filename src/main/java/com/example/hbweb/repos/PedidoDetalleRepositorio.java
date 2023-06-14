package com.example.hbweb.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.hbweb.model.PedidoDetalle;


import com.example.hbweb.model.Usuario;

public interface PedidoDetalleRepositorio extends CrudRepository<PedidoDetalle, Integer>{

	PedidoDetalle findById(int id);
	List<PedidoDetalle> findAllByUsuario(Usuario usuario);
	 @Modifying
	    @Query("UPDATE PedidoDetalle pd SET pd.estado = :estado WHERE pd.id = :id")
	    void setEstadoById(String estado, int id);
	
}
