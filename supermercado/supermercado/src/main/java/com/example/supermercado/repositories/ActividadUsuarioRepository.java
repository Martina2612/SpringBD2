package com.example.supermercado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.supermercado.modules.*;
import java.util.List;


public interface ActividadUsuarioRepository extends MongoRepository<ActividadUsuario, String>{
    List<ActividadUsuario> findByUsuarioId(String usuarioId);
    ActividadUsuario findTopByUsuarioIdOrderByInicioSesionDesc(String usuarioId);

}
