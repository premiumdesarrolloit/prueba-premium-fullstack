package com.eduar.dev.prueba.controllers;

import com.eduar.dev.prueba.services.authentication.AuthenticationService;
import com.eduar.dev.prueba.wrappers.request.LoginDto;
import com.eduar.dev.prueba.wrappers.request.UserDto;
import com.eduar.dev.prueba.wrappers.response.HandlerResponse;
import com.eduar.dev.prueba.wrappers.response.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/registro")
    public ResponseEntity<HandlerResponse> register(@RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(authenticationService.registerUser(user));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto credentials) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(authenticationService.login(credentials));
    }
}
