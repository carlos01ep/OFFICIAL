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
import com.example.hbweb.form.ProductoForm;
import com.example.hbweb.form.RegistroForm;
import com.example.hbweb.model.Cliente;
import com.example.hbweb.model.Pedido;
import com.example.hbweb.model.Producto;
import com.example.hbweb.model.Usuario;
import com.example.hbweb.repos.ClienteRepositorio;
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
	private RolRepositorio rolRepositorio;
	@Autowired
	private ProductoRepositorio productoRepositorio;
	@Autowired
	private PedidoRepositorio pedidioRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;


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
		public String nuevoPedido(NuevoPedidoForm nuevoPedidoForm, Model modelo, HttpSession session, LoginForm loginForm) {
			if (session != null) {
				if (session.getAttribute("rol").equals("3")) {
					Iterable<Producto> itProducto = productoRepositorio.findByStockTrue();
					// Convertimos
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
		public String checkPedido(@Valid NuevoPedidoForm nuevoPedidoForm, BindingResult bindingResult, Model modelo,
				HttpSession session) {

			if (bindingResult.hasErrors()) {
				return "/nuevopedidodependiente";
			}
			List<String> listaProductosById = nuevoPedidoForm.getIdProductos();
			List<String> listaCantidades = nuevoPedidoForm.getCantidadProductos();
			System.out.println("Esto es lo que llega del formulario:" + listaProductosById);
			System.out.println("Esto es lo que llega del formulario:" + listaCantidades);
			// para filtrar las cantidades y eliminar vacios o ceros
			List<Integer> cantidadesSeleccionadas = new ArrayList<>();
			for (int i = 0; i < listaCantidades.size(); i++) {
				String cantidad = listaCantidades.get(i);
				if (cantidad != "") {// controlar aqui si es != 0
					if (cantidad != "0") {
						cantidadesSeleccionadas.add(Integer.parseInt(cantidad));
					}
				}
			}
			System.out.println("Esto es lo que llega del formulario:" + cantidadesSeleccionadas);

			Usuario usuarioPedido = usuarioRepositorio.findByEmail((String) session.getAttribute("usuario"));
			Cliente nuevoCliente = new Cliente();
			clienteRepositorio.save(nuevoCliente);
			int posCant = 0;
			for (String idProducto : listaProductosById) {
				Pedido nuevoPedido = new Pedido(usuarioPedido, nuevoCliente, cantidadesSeleccionadas.get(posCant));

				Producto producto = productoRepositorio.findById(Integer.parseInt(idProducto));
				// formateo de Importe:

				// double importe = cantidadesSeleccionadas.get(posCant).doubleValue() *
				// producto.getPrecio();
				// DecimalFormat df = new DecimalFormat("#.##");
				// String importeFormateado = df.format(importe);
				// double importeFormateadoDouble = Double.parseDouble(importeFormateado);

				nuevoPedido.setImporteTotal(cantidadesSeleccionadas.get(posCant).doubleValue() * producto.getPrecio());
				nuevoPedido.getListaProductos().add(producto);
				producto.getListaPedido().add(nuevoPedido);
				pedidioRepositorio.save(nuevoPedido);
				posCant++;
			}

			return "redirect:/listapedido";

		}
}
