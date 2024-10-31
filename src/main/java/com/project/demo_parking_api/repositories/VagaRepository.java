package com.project.demo_parking_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo_parking_api.entity.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

	Optional<Vaga> findByCodigo(String codigo);

	Optional<Vaga> findFirstByStatus(Vaga.StatusVaga statusVaga);
}
