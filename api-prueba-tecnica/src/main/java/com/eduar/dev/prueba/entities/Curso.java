package com.eduar.dev.prueba.entities;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Table(name = "mnt_curso")
@Entity
@Builder
public class Curso{

    @Id
    @Column(name = "id", length = 26, unique = true, nullable = false)
    private String id = generateULID();

    private String nombre;
    private String categoria;

    @Column(name = "evaluacion_totales")
    private int evaluacionesTotales;

    @Column(precision = 3, scale = 1)
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "10.0", inclusive = true)
    private BigDecimal puntuacionPromedio;

    private int inscritos;

    @Column(precision = 4, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal indiceCalidad;

    public Curso() {
    }

    public Curso(String id, String nombre, String categoria, int evaluacionesTotales, BigDecimal puntuacionPromedio, int inscritos, BigDecimal indiceCalidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.evaluacionesTotales = evaluacionesTotales;
        this.puntuacionPromedio = puntuacionPromedio;
        this.inscritos = inscritos;
        this.indiceCalidad = indiceCalidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEvaluacionesTotales() {
        return evaluacionesTotales;
    }

    public void setEvaluacionesTotales(int evaluacionesTotales) {
        this.evaluacionesTotales = evaluacionesTotales;
    }

    public BigDecimal getPuntuacionPromedio() {
        return puntuacionPromedio;
    }

    public void setPuntuacionPromedio(BigDecimal puntuacionPromedio) {
        this.puntuacionPromedio = puntuacionPromedio;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public BigDecimal getIndiceCalidad() {
        return indiceCalidad;
    }

    public void setIndiceCalidad(BigDecimal indiceCalidad) {
        this.indiceCalidad = indiceCalidad;
    }

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

    public void calcularIndiceCalidad() {
        if (puntuacionPromedio == null || inscritos < 0) {
            this.indiceCalidad = BigDecimal.ZERO;
        }else {
            BigDecimal puntuacionComponent = puntuacionPromedio.multiply(BigDecimal.TEN);
            BigDecimal inscritosComponent = BigDecimal.valueOf(Math.log10(inscritos + 1)).multiply(BigDecimal.valueOf(5));

            this.indiceCalidad = puntuacionComponent.add(inscritosComponent).round(new MathContext(4, RoundingMode.HALF_UP));
        }
    }

}
