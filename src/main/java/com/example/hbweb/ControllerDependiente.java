package com.example.hbweb;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;



import com.example.hbweb.form.BuscarProducto;
import com.example.hbweb.form.LoginForm;
import com.example.hbweb.form.NuevoPedidoForm;
import com.example.hbweb.form.NuevoPedidoFormDependiente;
import com.example.hbweb.form.ProductoForm;
import com.example.hbweb.form.RegistroForm;
import com.example.hbweb.model.PedidoDetalle;
import com.example.hbweb.model.Pedido;
import com.example.hbweb.model.Producto;
import com.example.hbweb.model.Usuario;
import com.example.hbweb.repos.PedidoDetalleRepositorio;
import com.example.hbweb.repos.PedidoRepositorio;
import com.example.hbweb.repos.ProductoRepositorio;
import com.example.hbweb.repos.RolRepositorio;
import com.example.hbweb.repos.UsuarioRepositorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller

public class ControllerDependiente {



	@Autowired 
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private ProductoRepositorio productoRepositorio;
	@Autowired
	private PedidoRepositorio pedidioRepositorio;
	@Autowired
	private PedidoDetalleRepositorio pedidoDetalleRepositorio;


	@GetMapping("/homedependiente")
	public String homelink(HttpSession session, LoginForm loginForm, Model modelo) {
		if (session != null) {
			if (session.getAttribute("rol").equals("3")) {
	
				Iterable<Producto> itProducto = productoRepositorio.findAll();
				// Convertimos
				List<Producto> listaProducto = new ArrayList<Producto>();
				itProducto.forEach(listaProducto::add);
				List<Producto> listaProductoHome = listaProducto.subList(0, 3);
				List<Producto>listaProductoActivos = productoRepositorio.findByStockTrue(); //tiene que pasar a traves de un objeto iterable
				Collections.shuffle(listaProductoActivos);
				modelo.addAttribute("listaProductoHome", listaProductoHome);
				
				modelo.addAttribute("listaProductoActivos",listaProductoActivos);
				
				
				return "/homedependiente";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}
	// nuevopedido

	@GetMapping("/nuevopedidodependiente")
	public String nuevoPedido(Model modelo, HttpSession session) {
	    if (session != null) {
	        if (session.getAttribute("rol").equals("2") || session.getAttribute("rol").equals("3")) {
	            cargarProductos(modelo);
	            //modelo.addAttribute("loginForm", new LoginForm()); // Agregar el formulario al modelo
	            return "/nuevopedidodependiente";
	        } else {
	            session.setAttribute("rol", "");
	            session.invalidate();
	            return "/login";
	        }
	    } else {
	        return "/login";
	    }
	}

	@PostMapping(path = "/nuevopedidodependiente")
	@Transactional
	public String checkPedido(@Valid NuevoPedidoFormDependiente nuevoPedidoFormDependiente, BindingResult bindingResult, Model modelo, HttpSession session) {
	    if (bindingResult.hasErrors()) {
	        System.out.println("El formulario tiene errores");
	        modelo.addAttribute("bindingResult", bindingResult);
	        modelo.addAttribute("error", "Por favor, corrija los errores en el pedido.");
	        cargarProductos(modelo); // Agregar la carga de productos nuevamente
	        return "/nuevopedidodependiente";
	    }
		    
		    List<String> listaProductosByIdPares = nuevoPedidoFormDependiente.getIdProductosPares();
		    List<String> listaProductosByIdImpares = nuevoPedidoFormDependiente.getIdProductosImpares();
		    List<String> listaCantidades = nuevoPedidoFormDependiente.getCantidadProductos();
		    
		    double cambio= 0.0;
		    double total= 0.0;
		    double importePagado= 0.0;
		    if (nuevoPedidoFormDependiente.getCambio() != null && !nuevoPedidoFormDependiente.getCambio().isNaN()) {
		    	  cambio = nuevoPedidoFormDependiente.getCambio();
	
		    }
		    if (nuevoPedidoFormDependiente.getTotal() != null && !nuevoPedidoFormDependiente.getTotal().isEmpty()) {
		    	total = Double.parseDouble(nuevoPedidoFormDependiente.getTotal());
	
		    }
		    if (nuevoPedidoFormDependiente.getImportePagado() != null && !nuevoPedidoFormDependiente.getImportePagado().isNaN()) {
		    	importePagado = nuevoPedidoFormDependiente.getImportePagado();
	
		    }

		    
		    // Filtrar las cantidades y eliminar vacíos o ceros
		    List<Integer> cantidadesSeleccionadas = new ArrayList<>();
		    for (String cantidad : listaCantidades) {
		        if (!cantidad.isEmpty() && !cantidad.equals("0")) {
		            cantidadesSeleccionadas.add(Integer.parseInt(cantidad));
		        }
		    }
		    System.out.println("Esto es lo que llega del formulario (cantidades filtradas): " + cantidadesSeleccionadas);
		    
		    Usuario usuarioPedido = usuarioRepositorio.findByEmail((String) session.getAttribute("usuario"));
		   
		  

		    
		    
		    PedidoDetalle nuevoPedidoDetalle = new PedidoDetalle(usuarioPedido, total);
		    if (session.getAttribute("rol").equals("2")) {
		    	 nuevoPedidoDetalle.setEstado("pendiente de pago");
		    }else{
		    	nuevoPedidoDetalle.setEstado("abierto");
		    }
		    nuevoPedidoDetalle.setCambio(cambio);
		    nuevoPedidoDetalle.setImportePagado(importePagado);
		    PedidoDetalle nuevoPedidoDetalle2 = pedidoDetalleRepositorio.save(nuevoPedidoDetalle);

		    List<String> listaProductos = new ArrayList<>();

		    if (listaProductosByIdPares != null) {
		        listaProductos.addAll(listaProductosByIdPares);
		    }

		    if (listaProductosByIdImpares != null) {
		        listaProductos.addAll(listaProductosByIdImpares);
		    }
		    int posCant = 0;
		    for (String idProducto : listaProductos) {
		        Pedido nuevoPedido = new Pedido(nuevoPedidoDetalle, cantidadesSeleccionadas.get(posCant));
		        Producto producto = productoRepositorio.findById(Integer.parseInt(idProducto));
		        
		        nuevoPedido.setImporteTotal(cantidadesSeleccionadas.get(posCant).doubleValue() * producto.getPrecio());
		        nuevoPedido.getListaProductos().add(producto);
		        producto.getListaPedido().add(nuevoPedido);
		        
		        pedidioRepositorio.save(nuevoPedido);
		        
		        posCant++;
		    }

		    if (session.getAttribute("rol").equals("2")) {

		    	modelo.addAttribute("nuevoPedidoDetalle2", nuevoPedidoDetalle2);
		    	
		    	Iterable<Object[]> itConsulta = pedidioRepositorio.listaDetalleByPedidoDetalleQuerry(nuevoPedidoDetalle2.getId());
				List<Object[]> listaConsulta = new ArrayList<Object[]>();
				itConsulta.forEach(listaConsulta::add);
				modelo.addAttribute("listaConsulta", listaConsulta);
				
				modelo.addAttribute("nuevoPedidoDetalle2", nuevoPedidoDetalle2);
				
		    	 return "/pagopedido";
		    }else{
		    	 return "redirect:/detallepedido/" + nuevoPedidoDetalle2.getId();
		    }
		   
		}
		private void cargarProductos(Model modelo) {
		    Iterable<Producto> itProducto = productoRepositorio.findByStockTrue();
		    List<Producto> listaProducto = new ArrayList<>();
		    itProducto.forEach(listaProducto::add);

		    List<Producto> listaProductoPar = new ArrayList<>();
		    List<Producto> listaProductoImpar = new ArrayList<>();

		    if (listaProducto.size() % 2 == 0) {
		        listaProductoPar = listaProducto.subList(0, listaProducto.size() / 2);
		        listaProductoImpar = listaProducto.subList(listaProducto.size() / 2, listaProducto.size());
		    } else {
		        listaProductoPar = listaProducto.subList(0, listaProducto.size() / 2 + 1);
		        listaProductoImpar = listaProducto.subList(listaProducto.size() / 2 + 1, listaProducto.size());
		    }

		    modelo.addAttribute("listaProductoPar", listaProductoPar);
		    modelo.addAttribute("listaProductoImpar", listaProductoImpar);
		}
}
