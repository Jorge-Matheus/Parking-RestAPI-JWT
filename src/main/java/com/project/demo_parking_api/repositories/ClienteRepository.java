package com.project.demo_parking_api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo_parking_api.entity.Cliente;
import com.project.demo_parking_api.repositories.projection.ClienteProjection;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c")
	Page<ClienteProjection> findAllPageable(Pageable pageable);

	
	Cliente findByUsuarioId(Long id);
	
	Optional<Cliente> findByCpf(String cpf);
}
