package pe.com.ccodifica.ahsapp.models.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.com.ccodifica.ahsapp.models.entity.Cliente;



public interface IClienteService {

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	public Cliente save(Cliente cliente);

	public Cliente findOne(Integer id);

	public Page<Cliente> getByKeyword(String keywork, Pageable pageable);
	
	public Cliente findByRuc(BigInteger ruc);
	
	public List<Cliente> getByKeyword(String keywork);

}
