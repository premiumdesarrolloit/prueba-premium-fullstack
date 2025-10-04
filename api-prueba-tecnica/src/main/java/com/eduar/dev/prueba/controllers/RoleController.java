package com.eduar.dev.prueba.controllers;

import com.eduar.dev.prueba.entities.Role;
import com.eduar.dev.prueba.services.RoleService;
import com.eduar.dev.prueba.wrappers.request.RoleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(path = "/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(path = "")
    public ResponseEntity<Role> createRol(@RequestBody RoleDto rol) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(roleService.createRol(rol));
    }

    @GetMapping(path = "")
    public ResponseEntity<List<Role>> listRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.findAll());
    }

    @GetMapping(path = "/{id_role}")
    public ResponseEntity<Role> findRoleById(@PathVariable(value = "id_role") String id_role) {
        return this.roleService.findById(id_role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
