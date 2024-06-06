package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ActividadUsuarioRepository extends MongoRepository<ActividadUsuario, String>{
    List<ActividadUsuario> findByUsuarioId(String usuarioId);
    ActividadUsuario findTopByUsuarioIdOrderByInicioSesionDesc(String usuarioId);

}
