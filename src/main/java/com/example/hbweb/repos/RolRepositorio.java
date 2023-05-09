package com.example.hbweb.repos;
import com.example.hbweb.model.Rol;
import org.springframework.data.repository.CrudRepository;


public interface RolRepositorio extends CrudRepository<Rol, Integer>{

	Rol findByTipo(Integer tipo);
	
}
