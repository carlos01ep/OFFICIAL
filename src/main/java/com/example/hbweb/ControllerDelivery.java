package com.example.hbweb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.hbweb.form.DetallePedidoForm;
import com.example.hbweb.form.LoginForm;
import com.example.hbweb.model.PedidoDetalle;
import com.example.hbweb.model.Producto;
import com.example.hbweb.model.Rol;
import com.example.hbweb.model.Usuario;
import com.example.hbweb.repos.PedidoDetalleRepositorio;
import com.example.hbweb.repos.PedidoRepositorio;
import com.example.hbweb.repos.ProductoRepositorio;
import com.example.hbweb.repos.RolRepositorio;
import com.example.hbweb.repos.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControllerDelivery {
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

	@GetMapping("/homedelivery")
	public String homeDeliverylink(HttpSession session, LoginForm loginForm, Model modelo) {
		if (session != null) {
			if (session.getAttribute("rol").equals("4")) {
				String email = (String) session.getAttribute("usuario");
				List<PedidoDetalle> listaConsulta = new ArrayList<>();
				Usuario usuarioLoged = usuarioRepositorio.findByEmail(email);

				listaConsulta = (List<PedidoDetalle>) pedidoDetalleRepositorio.findAllByUsuarioAndEstadoListo(usuarioLoged);

				modelo.addAttribute("listaConsulta", listaConsulta);
				return "/homedelivery";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}
	@GetMapping("/recogerpedido/{id}/{minutos}")
	public String recogerPedido(@PathVariable String id, @PathVariable String minutos, Model modelo,
			HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("4")) {
				pedidoDetalleRepositorio.setEstadoById("en camino", Integer.parseInt(id));
				String horaEstimada = sumarMinutosAHoraActual(Integer.parseInt(minutos));
				pedidoDetalleRepositorio.setHoraPrevistaDeLlegadaById(horaEstimada, Integer.parseInt(id));
				return "redirect:/homedelivery";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}
	@GetMapping("/finalizarpedido/{id}")
	public String finalizarPedido(@PathVariable String id, Model modelo,
			HttpSession session, LoginForm loginForm) {
		if (session != null) {
			if (session.getAttribute("rol").equals("4")) {
				pedidoDetalleRepositorio.setEstadoById("finalizado", Integer.parseInt(id));
				return "redirect:/homedelivery";
			} else {
				session.setAttribute("rol", "");
				session.invalidate();
				return "/login";
			}
		} else {
			return "/login";
		}
	}

	 private String sumarMinutosAHoraActual(int minutos) {
	        LocalDateTime horaActual = LocalDateTime.now();
	        LocalDateTime horaConMinutosSumados = horaActual.plusMinutes(minutos);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        return horaConMinutosSumados.format(formatter);
	    }
}
