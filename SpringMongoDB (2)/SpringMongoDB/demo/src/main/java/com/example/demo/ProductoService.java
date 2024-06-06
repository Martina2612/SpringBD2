package com.example.demo;

import java.util.List;

import org.neo4j.annotations.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public List<PrecioProducto> getPreciosPorProducto(Long productoId) {
        return PrecioRepository.findByProductoIdOrderByFechaDesc(productoId);
    }
}
