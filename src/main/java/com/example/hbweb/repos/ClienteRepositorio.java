package com.example.hbweb.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.hbweb.model.Cliente;

public interface ClienteRepositorio extends CrudRepository<Cliente, Integer>{
	Cliente findById(int id);
}
