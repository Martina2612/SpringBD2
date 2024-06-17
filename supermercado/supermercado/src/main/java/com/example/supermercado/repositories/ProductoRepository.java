package com.example.supermercado.repositories;

import com.example.supermercado.modules.Producto;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


public interface ProductoRepository extends Neo4jRepository<Producto,Long>{

    @Query ("MATCH (p:Producto {nombre: $nombreProducto}) "+
            "RETURN (p)")
    Producto mostrarProducto(String nombreProducto);

    Producto findByNombre(String nombre);
}
