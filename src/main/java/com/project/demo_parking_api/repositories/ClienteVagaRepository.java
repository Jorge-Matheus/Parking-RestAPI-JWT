package com.project.demo_parking_api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.repositories.projection.ClienteVagaProjection;

@Repository
public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long> {

	Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

	long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

	Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);

	Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);

}
