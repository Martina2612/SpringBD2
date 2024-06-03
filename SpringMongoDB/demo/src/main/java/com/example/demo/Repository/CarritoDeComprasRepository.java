package com.example.demo.Repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.demo.CarritoDeCompras;

public interface CarritoDeComprasRepository extends Neo4jRepository<CarritoDeCompras, Long> {
    CarritoDeCompras findByUsuarioId(String usuarioId);
}
