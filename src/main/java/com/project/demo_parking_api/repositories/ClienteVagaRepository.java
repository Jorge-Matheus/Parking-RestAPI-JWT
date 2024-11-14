package com.project.demo_parking_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo_parking_api.entity.ClienteVaga;

@Repository
public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long> {

	Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

	long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

}
