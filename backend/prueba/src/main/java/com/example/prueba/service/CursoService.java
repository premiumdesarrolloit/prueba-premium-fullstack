package com.example.prueba.service;

import com.example.prueba.model.Curso;
import com.example.prueba.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    

    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }
      public List<Curso> obtenerTodosIndice() {
        return cursoRepository.findAllOrderByIndiceCalidadDesc();
    }
    
    

    public Optional<Curso> obtenerPorId(Long id) {
        return cursoRepository.findById(id);
    }
    
    // Guardar producto (crear o actualizar)
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }
    
    // Eliminar producto
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
    
    // Buscar productos por nombre
    public List<Curso> buscarPorNombre(String nombre) {
        return cursoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}