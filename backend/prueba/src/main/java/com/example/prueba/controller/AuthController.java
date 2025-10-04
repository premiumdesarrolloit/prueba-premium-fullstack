package com.example.prueba.controller;

import com.example.prueba.model.AppUser;
import com.example.prueba.service.AppUserService;
import com.example.prueba.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthController(AppUserService userService, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String user = body.get("username");
        String pass = body.get("password");
        String rolIdStr = body.get("rolId"); // <- extraemos rolId del JSON

    Long rolId = (rolIdStr != null) ? Long.parseLong(rolIdStr) : null;

    AppUser nuevo = userService.register(user, pass, rolId);


        return ResponseEntity.ok(nuevo);
    }

 

     @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");

    try {
        if (userService.validateCredentials(username, password)) {
            String token = jwtUtil.generateToken(username);

            return ResponseEntity.ok(Map.of(
                "statusCode", HttpStatus.OK.value(),  
                "token", token,
                "username", username
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "statusCode", HttpStatus.UNAUTHORIZED.value(),  
                "message", "Credenciales inválidas"
            ));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value(),   
            "message", "Error en autenticación"
        ));
    }
}

}
