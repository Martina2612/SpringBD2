package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.ActividadUsuario;

import java.util.List;


public interface ActividadUsuarioRepository extends MongoRepository<ActividadUsuario, String>{
    List<ActividadUsuario> findByUsuarioId(String usuarioId);
    ActividadUsuario findTopByUsuarioIdOrderByInicioSesionDesc(String usuarioId);

}
