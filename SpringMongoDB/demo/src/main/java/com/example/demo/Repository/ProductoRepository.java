package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.demo.Producto;

public interface ProductoRepository extends Neo4jRepository<Producto, Long> {
    List<Producto> findByNombre(String nombre);
}
