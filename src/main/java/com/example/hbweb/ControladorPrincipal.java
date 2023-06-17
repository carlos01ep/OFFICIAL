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
import com.example.hbweb.form.DetallePedidoForm;
import com.example.hbweb.form.ProductoForm;
import com.example.hbweb.form.RegistroForm;
import com.example.hbweb.model.PedidoDetalle;
import com.example.hbweb.model.Pedido;
import com.example.hbweb.model.Producto;
import com.example.hbweb.model.Rol;
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
public class ControladorPrincipal implements ErrorController {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	@Autowired
	private RolRepositorio rolRepositorio;
	@Autowired
	private ProductoRepositorio productoRepositorio;
	@Autowired
	private PedidoRepositorio pedidioRepositorio;
	@Autowired
	private PedidoDetalleRepositorio pedidoDetalleRepositorio;

	// gets
	/*
	 * @GetMapping("/pruebas") public String pagPruebas() {
	 * 
	 * return "pruebas"; }
	 */

	@GetMapping("/home")
	public String homelink(HttpSession session, LoginForm loginForm, Model modelo) {
		if (session != null) {
			if (session.getAttribute("rol").equals("2")) {

				Iterable<Producto> itProducto = productoRepositorio.findAll();
				// Convertimos
				List<Producto> listaProducto = new ArrayList<Producto>();
				itProducto.forEach(listaProducto::add);
				List<Producto> listaProductoHome = listaProducto.subList(0, 3);
				List<Producto> listaProductoActivos = productoRepositorio.findByStockTrue(); // tiene que pasar a traves
																								// de un objeto iterable
				Collections.shuffle(listaProductoActivos);
				modelo.addAttribute("listaProductoHome", listaProductoHome);

				modelo.addAttribute("listaProductoActivos", listaProductoActivos);

				return "/home";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}

	@GetMapping("/homeadmin")
	public String homeAdminlink(HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				return "/homeadmin";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}

	// login
	@GetMapping("/")
	public String loginindex(LoginForm loginForm, HttpSession session) {

		return "/login";
	}

	@GetMapping("/login")
	public String login(LoginForm loginForm, HttpSession session) {

		return "/login";
	}

	@PostMapping("/login")

	public String checkLogin(@Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session,
			Model model) {
		System.out.println("En login post");
		if (bindingResult.hasErrors()) {
			session.setAttribute("error", "Correo / Contraseña no válidos.");
			System.out.println("Error de validacion");
			return "/login";
		}
		System.out.println("En login post loginForm.getUserName()= " + loginForm.getEmail());
		try {
			Usuario loginUsuario = usuarioRepositorio.findByEmail(loginForm.getEmail());

			if (usuarioRepositorio.findByEmail(loginForm.getEmail()) != null
					&& loginUsuario.getContraseña().equals(loginForm.getPassword())) {
				//
				System.out.println("usuario encontrado, todo correcto");
				// inico session
				session.setAttribute("usuario", loginForm.getEmail());
				session.setAttribute("usuario_name", usuarioRepositorio.findByEmail(loginForm.getEmail()).getNombre());
				String rolUser = String.valueOf(loginUsuario.getRol().getTipo());
				session.setAttribute("rol", rolUser);
				session.setAttribute("error", "");

				if (loginUsuario.getRol().getTipo() == 1) {
					session.setAttribute("rol_usuario", "admin");
					return "redirect:/homeadmin";
				} else if (loginUsuario.getRol().getTipo() == 2) {
					session.setAttribute("rol_usuario", "usuario");
					return "redirect:/home";

				} else if (loginUsuario.getRol().getTipo() == 3) {
					session.setAttribute("rol_usuario", "usuariodependiente");
					return "redirect:/homedependiente";
				} else if (loginUsuario.getRol().getTipo() == 4) {
					session.setAttribute("rol_usuario", "usuariodelivery");
					return "redirect:/homedelivery";

				} else {
					return "redirect:/home";

				}
			}

		} catch (Exception e) {
			session.setAttribute("error", "Correo / Contraseña no válidos.");
			return "/login";
		}
		session.setAttribute("error", "Correo / Contraseña no válidos.");
		return "/login";
	}

	// logout
	@GetMapping("/logout")
	public String cerrarsesion(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {

			session.setAttribute("rol", "");
			session.invalidate();

		}
		return "redirect:/login";
	}
	// Editar producto

	@GetMapping("/registro/{id}")
	public String editarUsuario(@PathVariable String id, RegistroForm registroForm, Model modelo, HttpSession session,
			LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				Usuario usuarioEditar = usuarioRepositorio.findByEmail(id);
				registroForm.setEmail(id);
				registroForm.setPassword(usuarioEditar.getContraseña());
				registroForm.setNombre(usuarioEditar.getNombre());
				registroForm.setApellidos(usuarioEditar.getApellidos());
				registroForm.setTelefono(usuarioEditar.getTelefono());
				registroForm.setDni(usuarioEditar.getDni());
				registroForm.setRol(usuarioEditar.getRol().getTipo());
				registroForm.setIdEmail(id);
				modelo.addAttribute("registroForm", registroForm);
				modelo.addAttribute("idUsuario", id);
				return "/registro";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}

	}

	// registro
	@GetMapping("/registro")
	public String registro(RegistroForm registroForm, HttpSession session) {

		session.setAttribute("rol", "");
		session.setAttribute("error", "");
		session.invalidate();
		return "/registro";
	}

	@PostMapping(path = "/registro")
	public String checkRegistro(@Valid RegistroForm registroForm, BindingResult bindingResult, Model modelo,
			HttpSession session) {

		if (registroForm.getIdEmail() != "") {
			Usuario usuarioEditar = usuarioRepositorio.findByEmail(registroForm.getIdEmail());
			usuarioEditar.setEmail(registroForm.getEmail());
			// usuarioEditar.setContraseña(registroForm.getPassword());
			usuarioEditar.setDni(registroForm.getDni());
			usuarioEditar.setNombre(registroForm.getNombre());
			usuarioEditar.setApellidos(registroForm.getApellidos());

			usuarioEditar.setTelefono(registroForm.getTelefono());
			switch (registroForm.getRol()) {
			case 1:
				usuarioEditar.setRol(rolRepositorio.findByTipo(1));
				break;
			case 2:
				usuarioEditar.setRol(rolRepositorio.findByTipo(2));
				break;
			case 3:
				usuarioEditar.setRol(rolRepositorio.findByTipo(3));
				break;
			case 4:
				usuarioEditar.setRol(rolRepositorio.findByTipo(4));
				break;
			default:
				return "/error";
			}
			usuarioRepositorio.save(usuarioEditar);
			return "redirect:/listausuario";
		} else {
			if (bindingResult.hasErrors()) {
				session.setAttribute("error", "Datos no válidos.");
				return "/registro";
			}
			// el usuario ya existe
			if (usuarioRepositorio.findByEmail(registroForm.getEmail()) != null) {
				session.setAttribute("error", "Ya existe una cuenta creada con ese correo.");
				return "/registro";
			} else {
				Usuario usuNuevo = new Usuario(registroForm.getEmail(), registroForm.getPassword(),
						registroForm.getDni(), registroForm.getNombre(), registroForm.getApellidos(),
						registroForm.getTelefono(), rolRepositorio.findById(2).get());
				usuarioRepositorio.save(usuNuevo);
				modelo.addAttribute("welcomemessage", "Usuario dado de alta. Ahora puedes iniciar sesión.");
				return "/welcome";
			}
		}
	}

	// alata de producto
	@GetMapping("/altaproducto")
	public String altaProducto(ProductoForm productoForm, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				return "/altaproducto";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}

	@PostMapping(path = "/altaproducto")
	public String checkProducto(@Valid ProductoForm productoForm, BindingResult bindingResult, Model modelo,
			HttpSession session) {
		if (productoForm.getId() != "") {// si Id existe entonces se procede a editar
			Producto productoEditar = productoRepositorio.findById(Integer.parseInt(productoForm.getId()));
			productoEditar.setNombre(productoForm.getNombre());
			productoEditar.setCategoria(productoForm.getCategoria());
			productoEditar.setIngredientes(productoForm.getIngredientes());
			productoEditar.setPrecio(productoForm.getPrecio());
			productoEditar.setImage(productoForm.getImage());
			productoEditar.setStock(productoForm.isDisponible());
			productoRepositorio.save(productoEditar);
			return "redirect:/listaproducto";
		} else {// si Id no existe entonces se procede a crear un nuevo producto
			if (bindingResult.hasErrors()) {
				return "/altaproducto";
			}

			Usuario usuAux = usuarioRepositorio.findByEmail((String) session.getAttribute("usuario"));
			Producto productoNuevo = new Producto(productoForm.getNombre(), productoForm.getCategoria(),
					productoForm.getIngredientes(), productoForm.getPrecio(), productoForm.isDisponible(), usuAux);
			productoNuevo.setImage(productoForm.getImage());
			productoRepositorio.save(productoNuevo);
			return "redirect:/listaproducto";
		}

	}

// lista de productos
	@GetMapping("/listaproducto")
	public String showListaProductos(Model modelo, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				Iterable<Producto> itProducto = productoRepositorio.findAll();
				// Convertimos
				List<Producto> listaProducto = new ArrayList<Producto>();
				itProducto.forEach(listaProducto::add);
				modelo.addAttribute("listaProducto", listaProducto);
				return "/listaproducto";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}

// lista de usuarios
	@GetMapping("/listausuario")

	public String showListaUsuarios(Model modelo, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				Iterable<Usuario> itUsuario = usuarioRepositorio.findAll();

				List<Usuario> listaUsuarios = new ArrayList<Usuario>();
				itUsuario.forEach(listaUsuarios::add);
				modelo.addAttribute("listaUsuarios", listaUsuarios);
				return "/listausuario";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}

	}

	// Detalle de producto
	@GetMapping("/detalleproducto/{id}")
	public String verDetalleProducto(@PathVariable String id, Model model, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1") || session.getAttribute("rol").equals("2")
					|| session.getAttribute("rol").equals("3")) {
				Producto producto = productoRepositorio.findById(Integer.parseInt(id));

				model.addAttribute("producto", producto);

				return "/detalleproducto";
			} else {

				return "/login";
			}
		} else {
			return "/login";
		}

	}

	// nuevopedido

	@GetMapping("/nuevopedido")
	public String nuevoPedido(NuevoPedidoForm nuevoPedidoForm, Model modelo, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("2") || session.getAttribute("rol").equals("3")) {
				Iterable<Producto> itProducto = productoRepositorio.findByStockTrue();
				// Convertimos
				List<Producto> listaProducto = new ArrayList<Producto>();
				itProducto.forEach(listaProducto::add);
				if (listaProducto.size() % 2 == 0) {
					modelo.addAttribute("listaProducto", listaProducto);
				} else {
					Producto productoNull = new Producto();
					productoNull.setImage("undefined.png");
					listaProducto.add(productoNull);
					modelo.addAttribute("listaProducto", listaProducto);
				}
				return "/nuevopedido";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}

	}

	@PostMapping(path = "/nuevopedido")
	@Transactional
	public String checkPedido(@Valid NuevoPedidoForm nuevoPedidoForm, BindingResult bindingResult, Model modelo,
			HttpSession session) {

		if (bindingResult.hasErrors()) {
			return "/nuevopedido";
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
		PedidoDetalle nuevoPedidoDetalle = new PedidoDetalle(usuarioPedido, 0.0);
		pedidoDetalleRepositorio.save(nuevoPedidoDetalle);
		int posCant = 0;
		for (String idProducto : listaProductosById) {
			Pedido nuevoPedido = new Pedido(nuevoPedidoDetalle, cantidadesSeleccionadas.get(posCant));

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

	// lista de pedidos
	@GetMapping("/listapedido")
	public String showListaPedidos(Model modelo, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			String rol = (String) session.getAttribute("rol");
			String email = (String) session.getAttribute("usuario");
			List<PedidoDetalle> listaConsulta = new ArrayList<>();
			Usuario usuarioLoged = usuarioRepositorio.findByEmail(email);
			

			switch (rol) {
			case "1":
				listaConsulta = (List<PedidoDetalle>) pedidoDetalleRepositorio.findAll();
				break;
			case "2":
				listaConsulta = (List<PedidoDetalle>) pedidoDetalleRepositorio.findAllByUsuario(usuarioLoged);
				break;
			case "3":
				listaConsulta = (List<PedidoDetalle>) pedidoDetalleRepositorio
						.findAllByUsuarioAndEstadoPagadoAndEmpleado(usuarioLoged);
				break;
			case "4":
				listaConsulta = (List<PedidoDetalle>) pedidoDetalleRepositorio.findAllByUsuarioDelivery(usuarioLoged);
				break;
			default:
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}

			modelo.addAttribute("listaConsulta", listaConsulta);
			return "/listapedido";
		} else {
			return "/login";
		}
	}

	// Detalle de pedido
	@GetMapping("/detallepedido/{id}")
	public String verDetallePedido(DetallePedidoForm pagoPedidoForm, @PathVariable String id, Model modelo,
			HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1") || session.getAttribute("rol").equals("2")
					|| session.getAttribute("rol").equals("3") || session.getAttribute("rol").equals("4")) {
				Iterable<Object[]> itConsulta = pedidioRepositorio
						.listaDetalleByPedidoDetalleQuerry(Integer.parseInt(id));
				List<Object[]> listaConsulta = new ArrayList<Object[]>();
				itConsulta.forEach(listaConsulta::add);
				modelo.addAttribute("listaConsulta", listaConsulta);

				String email = (String) session.getAttribute("usuario");

				PedidoDetalle pedidoInfo = pedidoDetalleRepositorio.findById(Integer.parseInt(id));

				// controlar que asignar delivery solo lo pueda hacer el usuario que prepara el
				// pedido

				modelo.addAttribute("pedidoInfo", pedidoInfo);

				Rol rolDelivery = rolRepositorio.findByTipo(4);
				List<Usuario> usuariosRol4 = (List<Usuario>) usuarioRepositorio.findAllByRol(rolDelivery);

				modelo.addAttribute("usuariosRol4", usuariosRol4);

				modelo.addAttribute("id", id);
				if (session.getAttribute("rol").equals("3")) {
					if (pedidoInfo.getUsuarioEmpleado() == null) {
						return "/detallepedido";
					} else if (!pedidoInfo.getUsuarioEmpleado().getEmail().equals(email)) {
						return "redirect:/listapedido";
					}
				}
				return "/detallepedido";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}

	@PostMapping("/detallepedido/{id}")
	@Transactional
	public String checkDetallePedidoPagar(@Valid DetallePedidoForm detallePedidoForm, @PathVariable String id,
			BindingResult bindingResult, Model modelo, HttpSession session) {

		String rol = (String) session.getAttribute("rol");

		switch (rol) {
		case "1":

			break;
		case "2":
			if (detallePedidoForm.getDireccion() != "") {
				pedidoDetalleRepositorio.setDireccionById(detallePedidoForm.getDireccion(), detallePedidoForm.getId());
				pedidoDetalleRepositorio.setEstadoById("pagado", detallePedidoForm.getId());
			}
			break;
		case "3":
			if (detallePedidoForm.getEstado() != null && !detallePedidoForm.getEstado().isEmpty()) {
				pedidoDetalleRepositorio.setEstadoById(detallePedidoForm.getEstado(), detallePedidoForm.getId());
				String emailUsuario = (String) session.getAttribute("usuario");
				Usuario usuarioEmpleado = usuarioRepositorio.findByEmail(emailUsuario);
				pedidoDetalleRepositorio.setUsuarioEmpleadoById(usuarioEmpleado, detallePedidoForm.getId());
				return "redirect:/detallepedido/" + detallePedidoForm.getId();

			} else if (detallePedidoForm.getDelivery() != null && !detallePedidoForm.getDelivery().isEmpty()) {
				pedidoDetalleRepositorio.setEstadoById("listo", detallePedidoForm.getId());
				Usuario usuarioDelivery = usuarioRepositorio.findByEmail(detallePedidoForm.getDelivery());
				pedidoDetalleRepositorio.setUsuarioDeliveryById(usuarioDelivery, detallePedidoForm.getId());
				return "redirect:/detallepedido/" + detallePedidoForm.getId();
			} else {

			}
			break;
		case "4":

			break;
		default:
			session.setAttribute("rol", "");
			session.invalidate();
			return "/login";
		}

		if (session.getAttribute("rol").equals("2")) {
			pedidoDetalleRepositorio.setDireccionById(detallePedidoForm.getDireccion(), detallePedidoForm.getId());
			pedidoDetalleRepositorio.setEstadoById("pagado", detallePedidoForm.getId());
		} else if (session.getAttribute("rol").equals("3")) {
			if (detallePedidoForm.getEstado() != "") {
				try {
					pedidoDetalleRepositorio.setEstadoById(detallePedidoForm.getEstado(), detallePedidoForm.getId());
					String emailUsuario = (String) session.getAttribute("usuario");
					Usuario usuarioEmpleado = usuarioRepositorio.findByEmail(emailUsuario);
					pedidoDetalleRepositorio.setUsuarioEmpleadoById(usuarioEmpleado, detallePedidoForm.getId());
					return "redirect:/listapedido";
				} catch (Exception e) {
					return "redirect:/error";
				}
			}

		} else {

		}
		return "redirect:/listapedido";
	}

	// Editar producto

	@GetMapping("/altaproducto/{id}")
	public String editarProducto(@PathVariable String id, ProductoForm productoForm, Model modelo, HttpSession session,
			LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				Producto producto = productoRepositorio.findById(Integer.parseInt(id));
				productoForm.setId("" + id);
				productoForm.setNombre(producto.getNombre());
				productoForm.setCategoria(producto.getCategoria());
				productoForm.setIngredientes(producto.getIngredientes());
				productoForm.setPrecio(producto.getPrecio());
				productoForm.setImage(producto.getImage());
				productoForm.setDisponible(producto.isStock());

				modelo.addAttribute("productoForm", productoForm);
				modelo.addAttribute("idProducto", id);
				return "/altaproducto";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}

	}

	// eliminar producto
	@GetMapping("/eliminarproducto/{id}")
	public String eliminarProducto(@PathVariable String id, ProductoForm productoForm, Model modelo,
			HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				Producto producto = productoRepositorio.findById(Integer.parseInt(id));
				productoRepositorio.delete(producto);
				return "redirect:/listaproducto";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}

	}

	// BUSQUEDA DE PRODUCTO
	@GetMapping("/buscarproducto")
	public String showkBuscar(BuscarProducto buscarProducto, HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("1")) {
				return "/buscarproducto";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}

	}

	@PostMapping("/buscarproducto")
	public String checkBusqueda(@Valid BuscarProducto buscarProducto, BindingResult bindingResult, Model modelo,
			HttpSession session) {

		Iterable<Producto> itProducto = productoRepositorio.findByNombreAndCategoria(buscarProducto.getNombre(),
				buscarProducto.getCategoria());
		// Convertimos
		List<Producto> listaProducto = new ArrayList<Producto>();
		itProducto.forEach(listaProducto::add);
		modelo.addAttribute("listaProducto", listaProducto);
		return "redirect:/listaproducto";
	}

	// error
//	private static final String PATH = "/error";

	@GetMapping("/error")
	public String handleError() {
		return "/error";
	}

}