package com.eduar.dev.prueba.services;

import com.eduar.dev.prueba.entities.Role;
import com.eduar.dev.prueba.repositories.RoleRepository;
import com.eduar.dev.prueba.wrappers.request.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {


    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRol(RoleDto rol) {
        Role role = new Role();
        role.setNombre(rol.getNombre());
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(String id) {
        return roleRepository.findById(id);
    }
}
