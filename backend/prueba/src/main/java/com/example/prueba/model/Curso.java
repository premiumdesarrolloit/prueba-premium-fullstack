package com.example.prueba.model;
import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false)
    private String categoria;
    
    @Column(nullable = false)
    private Integer evaluacionesTotales;
    
    @Column(nullable = false)
    private Double puntuacionPromedio;
    
    @Column(nullable = false)
    private Integer inscritos;

    @Column(nullable = false)
    private Double indiceCalidad;
    
    
    // Constructores
    public Curso() {}
    
    public Curso(String nombre, String categoria, Integer evaluacionesTotales, Double puntuacionPromedio,Integer inscritos, Double indiceCalidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.evaluacionesTotales = evaluacionesTotales;
        this.puntuacionPromedio = puntuacionPromedio;
        this.inscritos = inscritos;
        this.indiceCalidad =indiceCalidad;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getEvaluacionesTotales() {
        return evaluacionesTotales;
    }

    public void setEvaluacionesTotales(Integer evaluacionesTotales) {
        this.evaluacionesTotales = evaluacionesTotales;
    }

    public Double getPuntuacionPromedio() {
        return puntuacionPromedio;
    }

    public void setPuntuacionPromedio(Double puntuacionPromedio) {
        this.puntuacionPromedio = puntuacionPromedio;
    }

    public Integer getInscritos() {
        return inscritos;
    }

    public void setInscritos(Integer inscritos) {
        this.inscritos = inscritos;
    }

    public Double getIndiceCalidad() {
        return indiceCalidad;
    }

    public void setIndiceCalidad(Double indiceCalidad) {
        this.indiceCalidad = indiceCalidad;
    }
    
   
}