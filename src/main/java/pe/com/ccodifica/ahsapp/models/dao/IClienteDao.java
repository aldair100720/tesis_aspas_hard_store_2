package pe.com.ccodifica.ahsapp.models.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import pe.com.ccodifica.ahsapp.models.entity.Cliente;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Integer>{
	
	@Query(value = "select s from Cliente s where s.ruc > 0 AND s.razonSocial like %?1%")
	 Page<Cliente> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query(value = "select s from Cliente s where s.ruc = ?1")
	 Cliente findByRuc(@Param("ruc") BigInteger ruc);
	
	@Query(value = "select s from Cliente s where s.ruc > 0 AND s.razonSocial like %?1")
	 List<Cliente> findByKeyword(@Param("keyword") String keyword);

}
