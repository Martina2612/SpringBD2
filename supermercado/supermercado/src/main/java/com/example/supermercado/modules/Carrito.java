package com.example.supermercado.modules;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node 
public class Carrito {
    @Id
    @GeneratedValue
    private Long idCarrito;

    private String idUsuario;
    private LocalDate fechaCreacion;


    public Long getIdCarrito() {
        return this.idCarrito;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
    public Carrito() {
    }


    public Carrito(Long idCarrito, String idUsuario, LocalDate fechaCreacion) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
    }

    
}
