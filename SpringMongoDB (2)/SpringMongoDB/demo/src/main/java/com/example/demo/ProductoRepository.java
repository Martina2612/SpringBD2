package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProductoRepository extends Neo4jRepository<Producto,Long>{
    Producto findByNombre(String nombre);
}
