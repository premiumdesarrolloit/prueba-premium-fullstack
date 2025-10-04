package com.example.prueba;

import com.example.prueba.model.Curso;
import com.example.prueba.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")  
@CrossOrigin(origins = "*")
public class CursoRestController {
    
   @Autowired
    private CursoService cursoService;


    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.obtenerTodos();
    }


    @GetMapping("/{id}")
    public Optional<Curso> obtenerProducto(@PathVariable Long id) {
        return cursoService.obtenerPorId(id);
    }

    @PostMapping
    public Curso guardarProducto(@RequestBody Curso producto) {
        return cursoService.guardar(producto);
    }

    // Actualizar curso
    @PutMapping("/{id}")
    public Curso actualizarProducto(@PathVariable Long id, @RequestBody Curso curso) {
        curso.setId(id);
        return cursoService.guardar(curso);
    }

    // Eliminar producto y retornar el eliminado
    @DeleteMapping("/{id}")
    public Optional<Curso> eliminarProducto(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.obtenerPorId(id);
        cursoService.eliminar(id);
        return curso;
    }

    // Buscar productos por nombre
    @GetMapping("/buscar")
    public List<Curso> buscarProductos(@RequestParam String nombre) {
        return cursoService.buscarPorNombre(nombre);
    }
}