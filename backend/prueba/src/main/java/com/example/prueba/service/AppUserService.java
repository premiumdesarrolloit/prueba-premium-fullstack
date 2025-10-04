package com.example.prueba.service;

import com.example.prueba.model.AppUser;
import com.example.prueba.model.Rol;
import com.example.prueba.repository.AppUserRepository;
import com.example.prueba.repository.RolRepository;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;
    private final RolRepository rolRepository;

    public AppUserService(AppUserRepository repo, PasswordEncoder encoder,RolRepository rolRepository) {
        this.repo = repo;
        this.encoder = encoder;
        this.rolRepository = rolRepository;
    }

    public AppUser register(String username, String rawPassword, long rolId) {
        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword));
        Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con id " + rolId));
            u.setRoles(Set.of(rol)); // asignamos el rol
        
        return repo.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
               .authorities(
                u.getRoles().stream()
                 .map(Rol::getNombre)      
                 .toArray(String[]::new)
            )
                .build();
    }

     public boolean validateCredentials(String username, String rawPassword) {

          System.out.println(username+" - "+rawPassword);
        return repo.findByUsername(username)
                .map(user -> encoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    public AppUser getByUsername(String username) {
    return repo.findByUsername(username).orElse(null);
}
}
