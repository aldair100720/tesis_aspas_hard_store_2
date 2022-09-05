package pe.com.ccodifica.ahsapp.models.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.ccodifica.ahsapp.models.dao.IClienteDao;
import pe.com.ccodifica.ahsapp.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		
		return clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Integer id) {
		
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> getByKeyword(String keywork,Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteDao.findByKeyword(keywork,pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findByRuc(BigInteger ruc) {
		
		return clienteDao.findByRuc(ruc);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> getByKeyword(String keywork) {
		// TODO Auto-generated method stub
		return clienteDao.findByKeyword(keywork);
	}

}
