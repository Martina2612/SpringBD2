package com.example.supermercado.modules;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection="actividades_administrador")
public class ActividadAdministrador {
    @Id 
    private String id;
    private Producto producto;
    private String valorAnterior;
    private String valorActual;
    private String nroOperador;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValorAnterior() {
        return this.valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorActual() {
        return this.valorActual;
    }

    public void setValorActual(String valorActual) {
        this.valorActual = valorActual;
    }


    public String getNroOperador() {
        return this.nroOperador;
    }

    public void setNroOperador(String nroOperador) {
        this.nroOperador = nroOperador;
    }    


    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ActividadAdministrador() {
    }


    public ActividadAdministrador(String id, Producto producto, String valorAnterior, String valorActual, String nroOperador) {
        this.id = id;
        this.producto = producto;
        this.valorAnterior = valorAnterior;
        this.valorActual = valorActual;
        this.nroOperador = nroOperador;
    }
    
    

}
