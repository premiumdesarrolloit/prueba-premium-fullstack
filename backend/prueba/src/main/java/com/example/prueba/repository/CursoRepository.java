package com.example.prueba.repository;


import com.example.prueba.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    // Buscar productos por nombre (método personalizado)
    List<Curso> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT c FROM Curso c ORDER BY c.indiceCalidad DESC")
    List<Curso> findAllOrderByIndiceCalidadDesc();
}