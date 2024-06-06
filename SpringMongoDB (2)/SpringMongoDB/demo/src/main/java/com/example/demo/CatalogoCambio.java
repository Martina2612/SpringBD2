package com.example.demo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class CatalogoCambio {
    @Id
    @GeneratedValue
    private Long id;

    private Long idProducto;
    private String campoModificado;
    private String valorAnterior;
    private String valorNuevo;
    private String operador;
    private Date fechaCambio;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getCampoModificado() {
        return this.campoModificado;
    }

    public void setCampoModificado(String campoModificado) {
        this.campoModificado = campoModificado;
    }

    public String getValorAnterior() {
        return this.valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return this.valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public String getOperador() {
        return this.operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Date getFechaCambio() {
        return this.fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }


    public CatalogoCambio() {
    }
    

    public CatalogoCambio(Long id, Long idProducto, String campoModificado, String valorAnterior, String valorNuevo, String operador, Date fechaCambio) {
        this.id = id;
        this.idProducto = idProducto;
        this.campoModificado = campoModificado;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.operador = operador;
        this.fechaCambio = fechaCambio;
    }

}
