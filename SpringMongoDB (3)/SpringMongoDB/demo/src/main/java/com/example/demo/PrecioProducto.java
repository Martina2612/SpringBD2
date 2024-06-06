package com.example.demo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="precios")
public class PrecioProducto {
    @Id
    private String id;
    private String productoId;
    private double precio;
    private Date fecha;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductoId() {
        return this.productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    

}
