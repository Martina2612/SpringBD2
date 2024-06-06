package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "actividades_usuarios")
public class ActividadUsuario {

    @Id
    private String id;
    private String usuarioId; // Referencia al usuario
    private LocalDateTime inicioSesion;
    private LocalDateTime finSesion;


     //Constructor 
     public ActividadUsuario() {
    }

    public ActividadUsuario(String usuarioId, LocalDateTime inicioSesion, LocalDateTime finSesion) {
        this.usuarioId = usuarioId;
        this.inicioSesion = inicioSesion;
        this.finSesion = finSesion;
    }

    //Getters y Setters
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getInicioSesion() {
        return this.inicioSesion;
    }

    public void setInicioSesion(LocalDateTime inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public LocalDateTime getFinSesion() {
        return this.finSesion;
    }

    public void setFinSesion(LocalDateTime finSesion) {
        this.finSesion = finSesion;
    }

    
}
