package com.example.demo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String direccion;
    private String documentoIdentidad;

    //Constructor vacio (necesario para MongoDB)
    public Usuario() {}

    //Constructor con todos los parametros
    public Usuario(String nombre, String direccion, String documentoIdentidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.documentoIdentidad = documentoIdentidad;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(String direccion){
        this.direccion=direccion;
    }

    public String getDocumentoIdentidad(){
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad){
        this.documentoIdentidad=documentoIdentidad;
    }
}

