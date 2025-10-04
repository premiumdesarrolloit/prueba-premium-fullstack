package com.eduar.dev.prueba.wrappers.request;

import java.math.BigDecimal;

public class CursoDto {


    private String nombre;
    private String categoria;
    private int evaluacionesTotales;

    private BigDecimal puntuacionPromedio;

    private int inscritos;


    public CursoDto() {
    }

    public CursoDto(String nombre, String categoria, int evaluacionesTotales, BigDecimal puntuacionPromedio, int inscritos) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.evaluacionesTotales = evaluacionesTotales;
        this.puntuacionPromedio = puntuacionPromedio;
        this.inscritos = inscritos;
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
}
