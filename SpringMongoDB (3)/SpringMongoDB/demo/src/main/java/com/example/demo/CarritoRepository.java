package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CarritoRepository extends Neo4jRepository<Carrito,Long>{
    Carrito findByUsuarioId(String usuarioId);
}
