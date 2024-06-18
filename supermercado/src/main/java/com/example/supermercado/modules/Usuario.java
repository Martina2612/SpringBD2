package com.example.supermercado.modules;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String usuario;
    private String contraseña;
    private String nombre;
    private String direccion;
    private String dni;
    private String categoria;

    //Constructor vacio (necesario para MongoDB)
    public Usuario() {}

    //Constructor con todos los parametros

    public Usuario(String usuario, String contraseña, String nombre, String direccion, String dni, String categoria) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.direccion = direccion;
        this.dni = dni;
        this.categoria=categoria;
    }
    

    // Getters y Setters

    public String getId() {
        return usuario;
    }

    public void setId(String id) {
        this.usuario = id;
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


    public String getContraseña() {
        return this.contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }


    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}

