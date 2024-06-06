package com.example.demo;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class ProductoCarritoService{

    final Driver neo4jDriver;

    public ProductoCarritoService(Driver neo4jDriver) {
        this.neo4jDriver = neo4jDriver;
    }

    @PostMapping("/carritos/{idUsuario}/cantidad/{nombreProducto}")
    public int obtenerCantidadDeProductoEnCarrito(String idCarrito, String nombre) {
        try (Session session = neo4jDriver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                String query = "MATCH (c:Carrito)-[r:CONTIENE]->(p:Producto) " +
                        "WHERE c.idUsuario = $idCarrito AND p.nombre = $nombre " +
                        "RETURN r.cantidad AS cantidad";
                Result result = tx.run(query, Values.parameters("idCarrito", idCarrito, "nombre", nombre));
                if (result.hasNext()) {
                    Record record = result.next();
                    return record.get("cantidad").asInt();
                } else {
                    // Si no se encuentra la relación, se puede devolver 0 o lanzar una excepción, dependiendo de tus necesidades
                    return 0;
                }
            }
        }
    }
}