package com.example.hbweb.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.hbweb.model.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario, String> {

	Usuario findByEmail(String id);

}
