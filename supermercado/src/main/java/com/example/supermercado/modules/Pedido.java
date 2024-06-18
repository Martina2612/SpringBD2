package com.example.supermercado.modules;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String idPedido;
    private Carrito carrito;
    private Usuario usuario;
    private String condicionIVA;
    private double importeTotal;
    private double importeFinalTotal; //con descuentos e impuestos



    public String getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Carrito getCarrito() {
        return this.carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCondicionIVA() {
        return this.condicionIVA;
    }

    public void setCondicionIVA(String condicionIVA) {
        this.condicionIVA = condicionIVA;
    }

    public double getImporteTotal() {
        return this.importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public double getImporteFinalTotal() {
        return this.importeFinalTotal;
    }

    public void setImporteFinalTotal(double importeFinalTotal) {
        this.importeFinalTotal = importeFinalTotal;
    }



    public Pedido() {
    }


    public Pedido(String idPedido, Carrito carrito, Usuario usuario, String condicionIVA, double importeTotal, double importeFinalTotal) {
        this.idPedido = idPedido;
        this.carrito = carrito;
        this.usuario = usuario;
        this.condicionIVA = condicionIVA;
        this.importeTotal = importeTotal;
        this.importeFinalTotal = importeFinalTotal;
    }

}
