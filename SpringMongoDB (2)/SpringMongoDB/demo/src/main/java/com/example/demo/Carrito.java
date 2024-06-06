package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Carrito {
    @Id
    @GeneratedValue
    private Long id;

    private String usuarioId;
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    private LocalDate fechaCreacion;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public Carrito() {
    }


    public Carrito(Long id, String usuarioId, LocalDate fechaCreacion, Set<ProductoCantidad> productos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaCreacion = fechaCreacion;
        this.productos = productos;
    }

    

    @Relationship(type="CONTIENE")
    private Set<ProductoCantidad> productos;

    public void agregarProducto(Producto producto, int cantidad){
        if(cantidad <= producto.getStock()){
            if(productos==null){
                productos=new HashSet<>();
            }
            productos.add(new ProductoCantidad(producto,cantidad));
            producto.setStock(producto.getStock()-cantidad);
        }
        }

    public void eliminarProducto(Producto producto){
        if(productos!= null){
            productos.removeIf(pc->pc.getProducto().equals(producto));
        }
    }

    public List<ProductoCantidad> getProductosConCantidad() {
        List<ProductoCantidad> productosConCantidad = new ArrayList<>();
        for (ProductoCantidad pc : productos) {
            productosConCantidad.add(pc);
        }
        return productosConCantidad;
    }

    public Set<ProductoCantidad> getProductos() {
        return productos;
    }

    public CarritoMemento guardarEstado() {
        return new CarritoMemento(this);
    }

    public void restaurarEstado(CarritoMemento memento) {
        this.id = memento.getEstadoGuardado().getId();
        this.usuarioId = memento.getEstadoGuardado().getUsuarioId();
        this.productos = memento.getEstadoGuardado().getProductos();
    }
}

