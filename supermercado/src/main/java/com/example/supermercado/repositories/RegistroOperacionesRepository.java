package com.example.supermercado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.supermercado.modules.RegistroOperaciones;

public interface RegistroOperacionesRepository extends MongoRepository<RegistroOperaciones, String> {
}
