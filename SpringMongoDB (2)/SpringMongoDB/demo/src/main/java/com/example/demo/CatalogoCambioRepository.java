package com.example.demo;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CatalogoCambioRepository extends Neo4jRepository<CatalogoCambio, Long> {
    List<CatalogoCambio> findByIdProductoOrderByFechaCambioDesc(Long idProducto);
}
