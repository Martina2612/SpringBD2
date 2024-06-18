package com.example.supermercado.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.example.supermercado.modules.Carrito;


public interface CarritoRepository extends Neo4jRepository<Carrito,Long>{

    Carrito findByIdUsuario(String idUsuario);

}
