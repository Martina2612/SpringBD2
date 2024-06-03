package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
   List<Usuario> findByNombre(String nombre);

}
