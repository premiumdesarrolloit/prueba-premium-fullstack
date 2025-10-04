package com.eduar.dev.prueba.entities;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "mnt_role")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @Column(name = "id", length = 26, unique = true, nullable = false)
    private String id;

    @Column(name = "nombre")
    private String nombre;

    private String createULID() {
        return UlidCreator.getUlid().toString();
    }

    // Se asegura de generar un ID antes de persistir en la BD
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = createULID();
        }
    }
}
