package pe.com.ccodifica.ahsapp.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.com.ccodifica.ahsapp.models.entity.Cliente;
import pe.com.ccodifica.ahsapp.models.service.IClienteService;
import pe.com.ccodifica.ahsapp.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	///////////////////////
	//// METODOS //////////
	///////////////////////

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();

		cliente.setTipoCliente("J");

		model.put("raiz", "Clientes");
		model.put("subRaiz", "Crear");
		model.put("accion", "Crear Cliente");
		model.put("cliente", cliente);

		return "form_cliente";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				logger.info("Error-> El ID del cliente no existe en la BBDD!");
				return "redirect:/clientes/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			logger.info("Error-> El ID del cliente no puede ser cero!");
			return "redirect:/clientes/listar";
		}

		model.put("raiz", "Clientes");
		model.put("subRaiz", "Editar");
		model.put("accion", "Editar Clientes");
		model.put("cliente", cliente);
		// model.put("idiomas", idiomas);

		return "form_cliente";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {

			logger.info("Errores en los campos del formulario");

			logger.info("Valor de SessionStatus en Form Paciente es completo? -> " + status.isComplete());

			if (cliente.getId() != null) {
				model.addAttribute("raiz", "Clientes");
				model.addAttribute("subRaiz", "Editar");
				model.addAttribute("accion", "Editar Cliente");
				// model.addAttribute("idiomas", idiomas);

			} else {
				model.addAttribute("raiz", "Cliente");
				model.addAttribute("subRaiz", "Crear");
				model.addAttribute("accion", "Crear Cliente");
				// model.addAttribute("idiomas", idiomas);

			}

			model.addAttribute("titulo", "Formulario de Cliente");
			return "form_cliente";
		}

		Long datetime = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(datetime);
		
		logger.info("Valor de TIPO CLIENTE -> "+cliente.getTipoCliente());

		if (cliente.getId() != null) {
			
			cliente.setFechaUpdate(timestamp);
			if(cliente.getTipoCliente().equals("J")) {
				cliente.setDni(0);				
				
			}else if(cliente.getTipoCliente().equals("P")) {
				cliente.setRuc(new BigInteger("0"));
				cliente.setEstadoSunat("ACTIVO");
			}
			
		} else {

			cliente.setEstado("A");
			cliente.setFechaCreacion(new Date());
			cliente.setFechaUpdate(timestamp);
			
			if(cliente.getTipoCliente().equals("J")) {
				cliente.setDni(0);				
				
			}else if(cliente.getTipoCliente().equals("P")) {
				cliente.setRuc(new BigInteger("0"));
				cliente.setEstadoSunat("ACTIVO");
			}

		}

		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		
		

		Cliente clienteGuardado = clienteService.save(cliente);
		// colocamos el codigo q sera igual que el ID generado en la BD
		// colocar un UUID para implementaciones posteriores
		clienteGuardado.setCodigo(clienteGuardado.getId());
		clienteService.save(clienteGuardado);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/clientes/listar";
	}

	@RequestMapping(value = { "/buscar" }, method = RequestMethod.GET)
	public String buscar(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "keyword") String keyword, Model model) {

		logger.info("Valor de keyword en busqueda Clientes --> ".concat(keyword));

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Cliente> clientes = clienteService.getByKeyword(keyword, pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/clientes/listar", clientes);

		model.addAttribute("raiz", "Clientes");
		model.addAttribute("subRaiz", "Listar");
		model.addAttribute("tituloTabla", "CLIENTES");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);

		return "table_clientes";

		// return "";
	}

	// @GetMapping(value = {"/listar"})
	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/clientes/listar", clientes);

		model.addAttribute("raiz", "Clientes");
		model.addAttribute("subRaiz", "Listar");
		model.addAttribute("tituloTabla", "CLIENTES");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);

		return "table_clientes";
	}

}
