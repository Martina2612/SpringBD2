package com.example.supermercado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.supermercado.modules.Pedido;

public interface PedidosRepository extends MongoRepository<Pedido,String>{

}
