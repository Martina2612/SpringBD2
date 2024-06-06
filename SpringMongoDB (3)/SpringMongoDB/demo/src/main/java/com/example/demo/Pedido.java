package com.example.demo;

import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String id;
    private Carrito carrito;
    private Optional<Usuario> usuario;
    private double importeTotal;
    private double descuento;
    private double impuestos;
    // Falta condicion del IVA
    // Constructor, getters y setters


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return this.carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Optional<Usuario> getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Optional<Usuario> usuario2) {
        this.usuario = usuario2;
    }

    public double getImporteTotal() {
        return this.importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public double getDescuento() {
        return this.descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getImpuestos() {
        return this.impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }


    public Pedido() {
    }


    public Pedido(String id, Carrito carrito, Optional<Usuario> usuario, double importeTotal, double descuento, double impuestos) {
        this.id = id;
        this.carrito = carrito;
        this.usuario = usuario;
        this.importeTotal = importeTotal;
        this.descuento = descuento;
        this.impuestos = impuestos;
    }


}
