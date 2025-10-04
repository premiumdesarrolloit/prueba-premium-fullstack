package com.eduar.dev.prueba.services;

import com.eduar.dev.prueba.entities.Curso;
import com.eduar.dev.prueba.repositories.CursoRepository;
import com.eduar.dev.prueba.wrappers.exceptions.GlobalException;
import com.eduar.dev.prueba.wrappers.request.CursoDto;
import com.eduar.dev.prueba.wrappers.response.HandlerResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    //POST
    public Curso saveCurso(CursoDto cursoDto) {
        Curso curso = new Curso();
        curso.setNombre(cursoDto.getNombre());
        curso.setCategoria(cursoDto.getCategoria());
        curso.setEvaluacionesTotales(cursoDto.getEvaluacionesTotales());
        curso.setPuntuacionPromedio(cursoDto.getPuntuacionPromedio());
        curso.setInscritos(cursoDto.getInscritos());
        curso.calcularIndiceCalidad();
        return cursoRepository.save(curso);
    }

    //GET - ALL
    public List<Curso> findAll() {
        return this.cursoRepository.findAll();
    }

    //GET - ID
    public Optional<Curso> findById(String id) {
        return this.cursoRepository.findById(id);
    }

    //PUT - ID
    public HandlerResponse updateCurso(String id, CursoDto cursoUpdate) {

        Optional<Curso> optionalCurso = cursoRepository.findById(id);

        if (optionalCurso.isEmpty()) {
            // Si el curso no se encuentra, lanzamos una excepción o devolvemos un error
            throw new GlobalException("Curso no encontrado con ID: " + id);
        }

        Curso curso = optionalCurso.get();

        if (cursoUpdate.getNombre() != null) {
            curso.setNombre(cursoUpdate.getNombre());
        }

        if (cursoUpdate.getCategoria() != null) {
            curso.setCategoria(cursoUpdate.getCategoria());
        }

        if (cursoUpdate.getEvaluacionesTotales() != 0) {
            curso.setEvaluacionesTotales(cursoUpdate.getEvaluacionesTotales());
        }

        if (cursoUpdate.getPuntuacionPromedio() != null) {
            curso.setPuntuacionPromedio(cursoUpdate.getPuntuacionPromedio());
        }

        if (cursoUpdate.getInscritos() >= 0) {
            curso.setInscritos(cursoUpdate.getInscritos());
        } else {
            throw new GlobalException("El número de inscritos no puede ser negativo");
        }

        curso.calcularIndiceCalidad();

        this.cursoRepository.save(curso);

        return HandlerResponse.builder()
                .message("Curso actualizado correctamente")
                .build();
    }

    //DELETE - ID
    public HandlerResponse deleteById(String id) {
        if (this.cursoRepository.existsById(id)) {
            this.cursoRepository.deleteById(id);
            return HandlerResponse.builder().message("Curso eliminado correctamente").build();
        }else {
            throw new GlobalException("Curso no encontrado con ese ID" + id);
        }
    }



}
