package com.project.demo_parking_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo_parking_api.entity.Usuario;
import com.project.demo_parking_api.entity.Usuario.Role;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);

	
	@Query("select u.role from usuario u where u.username like :username")
	Usuario.Role findRoleByUsername(String username);

}
