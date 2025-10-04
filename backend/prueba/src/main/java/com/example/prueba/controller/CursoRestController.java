package com.example.prueba;

import com.example.prueba.model.Curso;
import com.example.prueba.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Curso> listarCursos() {
        return cursoService.obtenerTodosIndice();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Optional<Curso> obtenerCurso(@PathVariable Long id) {
        return cursoService.obtenerPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Curso guardarCurso(@RequestBody Curso curso) {
        double indiceCalidad = (curso.getPuntuacionPromedio() * 10) + (Math.log10(curso.getInscritos() + 1) * 5);
        curso.setIndiceCalidad(indiceCalidad);
        return cursoService.guardar(curso);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Curso actualizarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        curso.setId(id);
         double indiceCalidad = (curso.getPuntuacionPromedio() * 10) + (Math.log10(curso.getInscritos() + 1) * 5);
        curso.setIndiceCalidad(indiceCalidad);
        return cursoService.guardar(curso);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Optional<Curso> eliminarCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.obtenerPorId(id);
        cursoService.eliminar(id);
        return curso;
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Curso> buscarCursos(@RequestParam String nombre) {
        return cursoService.buscarPorNombre(nombre);
    }
}