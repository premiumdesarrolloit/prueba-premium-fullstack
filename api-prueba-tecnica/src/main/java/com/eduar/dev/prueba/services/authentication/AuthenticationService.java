package com.eduar.dev.prueba.services.authentication;

import com.eduar.dev.prueba.entities.Role;
import com.eduar.dev.prueba.entities.User;
import com.eduar.dev.prueba.repositories.UserRepository;
import com.eduar.dev.prueba.services.RoleService;
import com.eduar.dev.prueba.wrappers.exceptions.GlobalException;
import com.eduar.dev.prueba.wrappers.request.LoginDto;
import com.eduar.dev.prueba.wrappers.request.UserDto;
import com.eduar.dev.prueba.wrappers.response.HandlerResponse;
import com.eduar.dev.prueba.wrappers.response.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationService(UserRepository userRepository, RoleService roleService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }



    public HandlerResponse registerUser(UserDto user) {
        Set<Role> rolList = new HashSet<>();

        if (user.getRoles() == null) {
            log.info("La propiedad roles es nula");
            throw new GlobalException("La propiedad roles no puede estar null");
        }

        if (user.getRoles().isEmpty()) {
            log.info("La propiedad roles esta vacia");
            throw new GlobalException("La propiedad roles no puede estar vacia");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new GlobalException("Email " + user.getEmail() + " ya se encuentra registrado");
        }

        for (String rolId : user.getRoles()) {
            Optional<Role> optionalRol = roleService.findById(rolId);
            if (optionalRol.isPresent()) {
                rolList.add(optionalRol.get());
            }else {
                throw new GlobalException("El rol no existente con ese identificador " + rolId);
            }
        }

        User candidateUser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(rolList)
                .build();

        userRepository.save(candidateUser);

        return HandlerResponse
                .builder()
                .message("Usuario " + user.getEmail() + " registrado con exito!")
                .build();
    }

    public JwtResponse login(LoginDto credentials) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        User user = (User) auth.getPrincipal();

        return JwtResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

}
