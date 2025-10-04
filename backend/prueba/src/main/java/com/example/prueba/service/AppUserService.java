package com.example.prueba.service;

import com.example.prueba.model.AppUser;
import com.example.prueba.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public AppUser register(String username, String rawPassword) {
        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword));
        return repo.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities("USER")
                .build();
    }

     public boolean validateCredentials(String username, String rawPassword) {

          System.out.println(username+" - "+rawPassword);
        return repo.findByUsername(username)
                .map(user -> encoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
