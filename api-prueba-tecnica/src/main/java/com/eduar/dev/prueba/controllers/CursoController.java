package com.eduar.dev.prueba.controllers;

import com.eduar.dev.prueba.entities.Curso;
import com.eduar.dev.prueba.entities.User;
import com.eduar.dev.prueba.services.CursoService;
import com.eduar.dev.prueba.wrappers.request.CursoDto;
import com.eduar.dev.prueba.wrappers.request.UserDto;
import com.eduar.dev.prueba.wrappers.response.HandlerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/cursos")
public class CursoController {


    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }


    @GetMapping(path = "")
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.findAll());
    }



    @GetMapping("/{id_curso}")
    public ResponseEntity<Curso> cursoById(@PathVariable(value = "id_curso") String id_curso) {
        return this.cursoService.findById(id_curso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "")
    public ResponseEntity<Curso> createCurso(@RequestBody CursoDto cursoCreate) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(cursoService.saveCurso(cursoCreate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id_curso}")
    public ResponseEntity<HandlerResponse> updateCurso(@PathVariable(value = "id_curso") String id_curso, @RequestBody CursoDto cursoDto) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(this.cursoService.updateCurso(id_curso, cursoDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id_curso}")
    public ResponseEntity<HandlerResponse> deleteCurso(@PathVariable(value = "id_curso") String id_curso) {
        return ResponseEntity.status(HttpStatus.OK).body(this.cursoService.deleteById(id_curso));
    }






}
