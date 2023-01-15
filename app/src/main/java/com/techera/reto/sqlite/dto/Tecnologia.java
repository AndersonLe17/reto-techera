package com.techera.reto.sqlite.dto;

public class Tecnologia {
    private Integer id_tecnologia;
    private String nombre;
    private String descripcion;

    public Tecnologia() {
    }

    public Tecnologia(Integer id_tecnologia, String nombre) {
        this.id_tecnologia = id_tecnologia;
        this.nombre = nombre;
    }

    public Tecnologia(Integer id_tecnologia, String nombre, String descripcion) {
        this.id_tecnologia = id_tecnologia;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
}
