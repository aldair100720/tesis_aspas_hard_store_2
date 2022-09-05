package pe.com.ccodifica.ahsapp.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.ccodifica.ahsapp.models.entity.Cliente;
import pe.com.ccodifica.ahsapp.models.service.IClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	
	
	@Autowired
	private IClienteService clienteService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	
	
	@RequestMapping(value = {"/existe"}, method = RequestMethod.GET)
	public Map<String, Object> existeCliente(@RequestParam(name="term", required = false) String term) {
		
		logger.info("Valor de keyword en busqueda Pacientes buscarByIdConsulta --> ".concat("->"+term+"<-"));
		
		Map<String, Object> nodo = new HashMap<>();
		
		if(!StringUtils.hasText(term)) {
			return nodo;
		}
		
		logger.info("valor de la consulta -> " + clienteService.findByRuc(new BigInteger(term.trim()))); 
		
		if(clienteService.findByRuc(new BigInteger(term.trim())) != null) {
			nodo.put("ruc", term);
			nodo.put("existe", true);
		}else {
			nodo.put("ruc", term);
			nodo.put("existe", false);
		}
				

		return  nodo;
		
	}
	
	@RequestMapping(value = {"/buscar"}, method = RequestMethod.GET)
	public List<Cliente> buscar(@RequestParam(name="term", required = false) String term) {
		
		logger.info("Valor de keyword en busqueda Clientes --> ".concat("->"+term+"<-"));
		
		if(!StringUtils.hasText(term)) {
			return new ArrayList<Cliente>();
		}
		
		//prueba para busqueda por partes de palabra
		String[] palabras= term.trim().split(" ");
		String cad="";
		for(Object o : palabras) {
			cad= cad + (String)o + "%";
		}
		
		return clienteService.getByKeyword(cad);
		
		//return pacienteService.getByKeyword(cad);
		
	}
	
	@RequestMapping(value = {"/buscarByRuc"}, method = RequestMethod.GET)
	public Map<String, Object> buscarByRuc(@RequestParam(name="term", required = false) String term) {
		logger.info("Valor de keyword en busqueda Clientes By Ruc --> ".concat("->"+term+"<-"));
		
		Map<String, Object> nodo = new HashMap<>();
		
		if(!StringUtils.hasText(term)) {
			return nodo;
		}
		
		logger.info("valor de la consulta -> " + clienteService.findByRuc(new BigInteger(term.trim()))); 
		
		Cliente cliente = clienteService.findByRuc(new BigInteger(term.trim()));
		
		if(cliente != null) {
			nodo.put("cliente", cliente);
			nodo.put("respuesta", "OK");
		}else {
			nodo.put("cliente", term);
			nodo.put("respuesta", "NE");
		}
				

		return  nodo;
		
	}
	
	
	

}
