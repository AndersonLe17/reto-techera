package com.techera.reto.dto;

public class Curso {
    private Integer id_curso;
    private Integer id_tecnologia;
    private Tecnologia tecnologia;
    private String nombre;
    private String descripcion;

    public Curso() {
    }

    public Curso(Integer id_curso, Integer id_tecnologia, String nombre, String descripcion) {
        this.id_curso = id_curso;
        this.id_tecnologia = id_tecnologia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Curso(Integer id_tecnologia, String nombre, String descripcion) {
        this.id_tecnologia = id_tecnologia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Curso(Integer id_curso, Tecnologia tecnologia, String nombre, String descripcion) {
        this.id_curso = id_curso;
        this.tecnologia = tecnologia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId_curso() {
        return id_curso;
    }

    public void setId_curso(Integer id_curso) {
        this.id_curso = id_curso;
    }

    public Integer getId_tecnologia() {
        return id_tecnologia;
    }

    public void setId_tecnologia(Integer id_tecnologia) {
        this.id_tecnologia = id_tecnologia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tecnologia getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(Tecnologia tecnologia) {
        this.tecnologia = tecnologia;
    }

    @Override
    public String toString() {
        return this.id_curso + " - " + this.nombre + "\n" + this.tecnologia.getNombre();
    }
}
