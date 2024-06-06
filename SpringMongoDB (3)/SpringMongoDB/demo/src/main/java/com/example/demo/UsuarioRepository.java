package com.example.demo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
   List<Usuario> findByNombre(String nombre);
   Optional<Usuario> findById(String id);

}
