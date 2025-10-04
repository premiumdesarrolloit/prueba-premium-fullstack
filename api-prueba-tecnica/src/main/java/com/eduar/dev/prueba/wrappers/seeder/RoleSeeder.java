package com.eduar.dev.prueba.wrappers.seeder;

import com.eduar.dev.prueba.entities.Role;
import com.eduar.dev.prueba.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Verificar si ya existen roles
        if (roleRepository.count() == 0) {

            // Cargar roles y asociar permisos
            Role rolAdmin = new Role();
            Role rolUser = new Role();
            rolAdmin.setNombre("ROLE_ADMIN");
            rolUser.setNombre("ROLE_USER");


            roleRepository.save(rolAdmin);
            roleRepository.save(rolUser);
        }
    }
}
