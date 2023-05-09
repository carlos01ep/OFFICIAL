package com.example.hbweb.repos;
import com.example.hbweb.model.Producto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepositorio extends CrudRepository<Producto, Integer>{
	Producto findById(int id);
	
	List<Producto> findByNombreAndCategoria(String nombre, String categoria);
	Producto findByNombre(String nombre);
	List<Producto> findByStockTrue();
}
