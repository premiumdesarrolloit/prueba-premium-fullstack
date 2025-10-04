package com.eduar.dev.prueba.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Table(name = "mnt_user")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Column(name = "id", length = 26, unique = true, nullable = false)
    private String id = generateULID();

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mnt_user_rol",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNombre()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    // method generate random ulid 26 characters
    private String generateULID() {
        return UlidCreator.getUlid().toString();
    }

    // Se asegura de generar un ID antes de persistir en la BD
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = generateULID();
        }
    }
}
