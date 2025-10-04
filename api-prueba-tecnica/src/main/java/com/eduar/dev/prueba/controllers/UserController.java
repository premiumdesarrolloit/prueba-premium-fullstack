package com.eduar.dev.prueba.controllers;

import com.eduar.dev.prueba.entities.User;
import com.eduar.dev.prueba.services.UserService;
import com.eduar.dev.prueba.wrappers.response.HandlerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping(path = "/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping(path = "/{id_user}")
    public ResponseEntity<User> getUserById(@PathVariable String id_user) {
        log.info("Solicitud recibida para el usuario con ID: {}", id_user);
        return userService.findByUser(id_user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
