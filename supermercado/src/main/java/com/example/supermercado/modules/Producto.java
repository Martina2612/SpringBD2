package com.example.supermercado.modules;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Producto {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String descripcion;
    private String comentario;
    private int stock;
    private double precio;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentaro) {
        this.comentario = comentaro;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public Producto() {
    }


    public Producto(Long id, String nombre, String descripcion, String comentario, int stock, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.comentario = comentario;
        this.stock = stock;
        this.precio = precio;
    }

}
