package com.eduar.dev.prueba.wrappers.seeder;

import com.eduar.dev.prueba.entities.Role;
import com.eduar.dev.prueba.repositories.RoleRepository;
import com.eduar.dev.prueba.repositories.UserRepository;
import com.eduar.dev.prueba.services.authentication.AuthenticationService;
import com.eduar.dev.prueba.wrappers.request.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Order(2)
@Component
public class UserSeeder implements CommandLineRunner {

    private final  UserRepository userRepository;
    private final  RoleRepository roleRepository;
    private final AuthenticationService authenticationService;

    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Optional<Role> adminRole = roleRepository.findByName("ADMIN");

            if (adminRole.isPresent()) {
                UserDto user = new UserDto();
                user.setEmail("root@admin.com");
                user.setPassword("root.admin");
                user.setRoles(Set.of(adminRole.get().getId()));

                this.authenticationService.registerUser(user);
            }

        }
    }
}
