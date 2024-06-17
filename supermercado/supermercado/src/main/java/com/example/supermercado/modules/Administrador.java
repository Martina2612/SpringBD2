package com.example.supermercado.modules;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")


public class Administrador {
    @Id
	private String id;
	private String nombre;
	private String email;
	private String contraseña;
	private String nroOperador;


    public Administrador() {
    }


    public Administrador(String id, String nombre, String email, String contraseña, String nroOperador) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.nroOperador = nroOperador;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNroOperador() {
        return this.nroOperador;
    }

    public void setNroOperador(String nroOperador) {
        this.nroOperador = nroOperador;
    }

}
