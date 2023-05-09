package com.example.hbweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



import com.example.hbweb.model.Producto;
import com.example.hbweb.model.Rol;
import com.example.hbweb.model.Usuario;
import com.example.hbweb.repos.ProductoRepositorio;
import com.example.hbweb.repos.RolRepositorio;
import com.example.hbweb.repos.UsuarioRepositorio;

@SpringBootApplication
public class HbwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbwebApplication.class, args);
	}

	@Bean
	public CommandLineRunner datosIniciales(RolRepositorio rolRepositorio, UsuarioRepositorio usuarioRepositorio, ProductoRepositorio productoRepositorio) {
		
		return (args) ->{
			//cargamos en la BBDD roles clientes por defecto
			if (rolRepositorio.count()==0) {
				Rol rol1 = new Rol(1, "administrador");
				Rol rol2 = new Rol(2, "usuario");
				
				rolRepositorio.save(rol1);
				rolRepositorio.save(rol2);
				
				//cargamos en la BBDD usuarios por defecto
				
				Usuario usuario1 = new Usuario("admin01ep@gmail.com", "asdASD123", "12312312W", "admin1", "apellidoAdmin", "123123123",
						rol1);
				Usuario usuario2 = new Usuario("Usuario01ep@gmail.com", "asdASD123", "12312312W", "usuario1", "apellidoUsuario", "123123123",
						rol2);
				
				usuarioRepositorio.save(usuario1);
				usuarioRepositorio.save(usuario2);
				
				//cargamos en la BBDD productos por defecto
				
				
				Producto p1 = new Producto("Hubo chicken", "Hamburguesa", "pan, tomate, lechuga, queso, carne de res y cerdo, pollo, huevo", "hbmin1.png", 6.00, true, usuario1);
				Producto p2 = new Producto("Hubo triple", "Hamburguesa", "pan, tomate, carne de res y cerdo, lechuga, queso, cebolla, pepinillo", "hbmin2.png", 8.00, true, usuario1);
				Producto p3 = new Producto("Hubo doble", "Hamburguesa", "pan, tomate, carne de res y cerdo, lechuga, queso, cebolla", "hbmin3.png", 7.00, true, usuario1);
				Producto p4 = new Producto("Cocacola", "Bebida", "azucar", "hbmin4.png", 1.50, true, usuario1);
				Producto p5 = new Producto("Patatas fritas", "Complemento", "patatas, sal", "hbmin7.png", 2.00, true, usuario1);
				Producto p6 = new Producto("Cocacola Green", "Bebida", "azucar", "hbmin6.png", 1.50, true, usuario1);
				Producto p7 = new Producto("Patatas Hubo", "Complemento", "patatas, sal", "hbmin8.png", 2.00, true, usuario1);
				Producto p8 = new Producto("Cocacola Blue", "Bebida", "azucar", "hbmin5.png", 1.50, true, usuario1);
				productoRepositorio.save(p1);
				productoRepositorio.save(p2);
				productoRepositorio.save(p3);
				productoRepositorio.save(p4);
				productoRepositorio.save(p5);
				productoRepositorio.save(p6);
				productoRepositorio.save(p7);
				productoRepositorio.save(p8);
				
				
			}
			
		};
		
	}
}
