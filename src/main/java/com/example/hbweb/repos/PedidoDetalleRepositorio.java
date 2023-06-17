package com.example.hbweb.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.hbweb.model.PedidoDetalle;

import com.example.hbweb.model.Usuario;

import jakarta.transaction.Transactional;

public interface PedidoDetalleRepositorio extends CrudRepository<PedidoDetalle, Integer> {

	PedidoDetalle findById(int id);
	List<PedidoDetalle> findAllByUsuario(Usuario usuario);

//	List<PedidoDetalle> findAllByUsuarioDelivery(Usuario usuarioDelivery);
	
	@Query("SELECT pd FROM PedidoDetalle pd WHERE pd.usuario = :usuario OR pd.estado = 'pagado'")
	List<PedidoDetalle> findAllByUsuarioAndEstadoPagado(@Param("usuario") Usuario usuario);
	
	@Query("SELECT pd FROM PedidoDetalle pd WHERE (pd.usuario = :usuario OR pd.estado = 'pagado' OR pd.usuarioEmpleado = :usuario) AND pd.estado != 'finalizado'")
	List<PedidoDetalle> findAllByUsuarioAndEstadoPagadoAndEmpleado(@Param("usuario") Usuario usuario);
	
	@Query("SELECT pd FROM PedidoDetalle pd WHERE (pd.estado = 'listo' OR pd.estado = 'en camino') AND pd.usuarioDelivery = :usuario")
	List<PedidoDetalle> findAllByUsuarioAndEstadoListo(@Param("usuario") Usuario usuario);
	
	@Query("SELECT pd FROM PedidoDetalle pd WHERE pd.estado != 'finalizado' AND pd.usuarioDelivery = :usuario")
	List<PedidoDetalle> findAllByUsuarioDelivery(@Param("usuario") Usuario usuario);
	
	@Modifying
	@Transactional
	@Query("UPDATE PedidoDetalle pd SET pd.horaPrevistaDeLlegada = :horaPrevistaDeLlegada WHERE pd.id = :id")
	void setHoraPrevistaDeLlegadaById(@Param("horaPrevistaDeLlegada") String horaPrevistaDeLlegada, @Param("id") int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE PedidoDetalle pd SET pd.estado = :estado WHERE pd.id = :id")
	void setEstadoById(String estado, int id);
	@Transactional
	@Modifying
	@Query("UPDATE PedidoDetalle pd SET pd.direccion = :direccion WHERE pd.id = :id")
	void setDireccionById(String direccion, int id);

	@Modifying
	@Transactional
	@Query("UPDATE PedidoDetalle pd SET pd.usuarioDelivery = :usuarioDelivery WHERE pd.id = :id AND pd.usuarioDelivery IS NULL")
	void setUsuarioDeliveryById(@Param("usuarioDelivery") Usuario usuarioDelivery, @Param("id") int id);
	
	@Modifying
	@Transactional
	@Query("UPDATE PedidoDetalle pd SET pd.usuarioEmpleado = :usuarioEmpleado WHERE pd.id = :id AND pd.usuarioEmpleado IS NULL")
	void setUsuarioEmpleadoById(@Param("usuarioEmpleado") Usuario usuarioEmpleado, @Param("id") int id);

}
