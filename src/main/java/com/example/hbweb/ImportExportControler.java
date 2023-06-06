package com.example.hbweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.hbweb.form.ProductoForm;
import com.example.hbweb.model.Producto;
import com.example.hbweb.model.ProductoJAXB;
import com.example.hbweb.model.ProductosJAXB;
import com.example.hbweb.model.Usuario;
import com.example.hbweb.repos.ProductoRepositorio;
import com.example.hbweb.repos.UsuarioRepositorio;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class ImportExportControler {
	@Autowired
	private ProductoRepositorio productoRepositorio;
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@GetMapping("/productos.xml")
	public void exportarProductos(HttpServletResponse response) throws IOException, JAXBException {
		List<Producto> productosBBDD = (List<Producto>) productoRepositorio.findAll();

		List<ProductoJAXB> listaProducto = new ArrayList<>();

		for (Producto producto : productosBBDD) {
			ProductoJAXB nuevoProducto = new ProductoJAXB(producto.getNombre(), producto.getCategoria(),
					producto.getIngredientes(), producto.getImage(), producto.getPrecio(), producto.isStock());
			listaProducto.add(nuevoProducto);
		}

		String archivoProductos = "/productos.xml";

		JAXBContext context = JAXBContext.newInstance(ProductoJAXB.class, ProductosJAXB.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(new ProductosJAXB(listaProducto), response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment; filename=" + archivoProductos);
		response.setContentType("application/xml");

	}

	// alata de producto XML
	@GetMapping("/altaproductoxml")
	public String altaProductoXML(ProductoForm productoForm) {

		return "/altaproductoxml";
	}

	@PostMapping(path = "/altaproductoxml")
	public String checkProductoXML(Model modelo, HttpSession session,
			@RequestParam("file") MultipartFile file)
			throws JAXBException, IOException {
		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ProductosJAXB.class);
		Unmarshaller unmarshaller = (Unmarshaller) jaxbContext.createUnmarshaller();
		ProductosJAXB productosJAXB = (ProductosJAXB) unmarshaller.unmarshal(file.getInputStream());
		List<ProductoJAXB> listaProducto = productosJAXB.getProductos();

		Usuario usuAux = usuarioRepositorio.findByEmail((String) session.getAttribute("usuario"));

		for (ProductoJAXB productoJAXB : listaProducto) {

			Producto producto = new Producto(productoJAXB.getNombre(), productoJAXB.getCategoria(),
					productoJAXB.getIngredientes(), productoJAXB.getImage(), productoJAXB.getPrecio(),
					productoJAXB.isStock(), usuAux);

			productoRepositorio.save(producto);

		}
		modelo.addAttribute("mensaje", "El archivo se ha importado correctamente.");

		return "/homeadmin";

	}

}
