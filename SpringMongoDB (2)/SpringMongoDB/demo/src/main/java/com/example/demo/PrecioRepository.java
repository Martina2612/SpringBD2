package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrecioRepository extends MongoRepository<PrecioProducto, String> {
    static List<PrecioProducto> findByProductoIdOrderByFechaDesc(Long idProducto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByProductoIdOrderByFechaDesc'");
    }
}
