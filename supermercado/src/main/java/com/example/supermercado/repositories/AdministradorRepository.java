package com.example.supermercado.repositories;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.supermercado.modules.Administrador;


public interface AdministradorRepository extends MongoRepository<Administrador, String> {
    Optional<Administrador> findByEmail(String email);

}
