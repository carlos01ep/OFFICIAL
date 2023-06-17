package com.example.hbweb.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.hbweb.model.Rol;
import com.example.hbweb.model.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario, String> {

	Usuario findByEmail(String id);
	
	@Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> findAllByRol(@Param("rol") Rol rol);

}
