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
import com.example.hbweb.form.PagoPedidoForm;
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
public class ControllerPago {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private ProductoRepositorio productoRepositorio;
	@Autowired
	private PedidoRepositorio pedidioRepositorio;
	@Autowired
	private PedidoDetalleRepositorio pedidoDetalleRepositorio;

	@GetMapping("/pagopedido")
	public String getPagoPedido(HttpSession session, LoginForm loginForm, Model modelo) {
		if (session != null) {
			if (session.getAttribute("rol").equals("2")) {
				return "pagopedido";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "login";
			}
		} else {
			return "login";
		}
	}

	@PostMapping(path = "/pagopedido")
	@Transactional
	public String checkPagoPedido(@Valid PagoPedidoForm pagoPedidoForm, BindingResult bindingResult, Model modelo,
			HttpSession session) {
		if (session.getAttribute("rol").equals("2")) {
			pedidoDetalleRepositorio.setDireccionById(pagoPedidoForm.getDireccion(), pagoPedidoForm.getId());
			pedidoDetalleRepositorio.setEstadoById("pagado", pagoPedidoForm.getId());
		} else {
			
		}
		
		return "redirect:/listapedido";
	}

}
