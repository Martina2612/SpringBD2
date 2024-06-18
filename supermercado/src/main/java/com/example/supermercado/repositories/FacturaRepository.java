package com.example.supermercado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.supermercado.modules.Factura;
import java.util.List;


public interface FacturaRepository extends MongoRepository<Factura,String>{
    List<Factura> findByIdUsuario(String idUsuario);
}
