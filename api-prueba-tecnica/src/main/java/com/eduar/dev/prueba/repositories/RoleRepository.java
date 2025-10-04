package com.eduar.dev.prueba.repositories;

import com.eduar.dev.prueba.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query(value = "SELECT * FROM mnt_role r WHERE r.nombre = :nombre", nativeQuery = true)
    Optional<Role> findByName(@Param(value = "nombre") String nombre);
}
