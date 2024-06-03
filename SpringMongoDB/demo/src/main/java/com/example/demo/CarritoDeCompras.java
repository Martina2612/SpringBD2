package com.example.demo;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class CarritoDeCompras {
    @Id @GeneratedValue
    private Long idCarrito;
    private String usuarioId; //id del usuario

    @Relationship(type = "CONTIENE")
    private List<Contiene> contiene;


    public CarritoDeCompras() {
    }


    public CarritoDeCompras(Long idCarrito, String id, List<Contiene> contiene) {
        this.idCarrito = idCarrito;
        this.usuarioId = id;
        this.contiene = contiene;
    }
    

    public Long getIdCarrito() {
        return this.idCarrito;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }


    public String getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public List<Contiene> getContiene() {
        return this.contiene;
    }

    public void setContiene(List<Contiene> contiene) {
        this.contiene = contiene;
    }
    
    
}
