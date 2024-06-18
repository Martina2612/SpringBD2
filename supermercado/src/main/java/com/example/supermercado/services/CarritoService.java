package com.example.supermercado.services;

import java.time.LocalDate;
import java.util.Scanner;

import org.neo4j.driver.Record;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermercado.modules.Carrito;
import com.example.supermercado.repositories.CarritoRepository;
import com.example.supermercado.repositories.ProductoRepository;

@Service
public class CarritoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    public boolean existeCarrito(String usuario){
        return (carritoRepository.findByIdUsuario(usuario)!=null); //Devuelve true si existe el carrito
    }

    public void crearCarrito(String usuario){
        Carrito carrito= new Carrito();
        LocalDate fecha= LocalDate.now();
        carrito.setFechaCreacion(fecha);
        carrito.setIdUsuario(usuario);
        carritoRepository.save(carrito);
    }

    public boolean agregarProducto(Scanner sc, String usuario){
        if(!existeCarrito(usuario)){
            crearCarrito(usuario);
        }

        System.out.print("Ingrese el nombre del producto que desea agregar a su carrito: ");
        String nombre = sc.nextLine();

        //Verificar si un producto esta en el carrito
        boolean existe= existeProductoEnCarrito(nombre, usuario);
        if(existe==false){
            int stock= productoRepository.findByNombre(nombre).getStock();

            while (true) {
                System.out.print("Ingrese la cantidad. [Stock: " + stock + "] ---> ");
                String cantidadStr = sc.nextLine();
                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantidadStr);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido.");
                    continue; // Volver a pedir la cantidad si no es un número válido
                }
    
                if (cantidad > stock || cantidad<1) {
                    System.out.println("Error: La cantidad es incorrecta.");
                } else {
                    //Lógica para agregar el producto al carrito
                    try(Driver driver = GraphDatabase.driver("bolt://localhost:7687")){
                        try(Session session = driver.session()){
                            session.run("MATCH (c:Carrito {idUsuario: $usuario}), (p:Producto {nombre: $nombre})\n" + //
                                                                "WITH c, p, $cantidad AS cantidad\n" + //
                                                                "CREATE (c)-[contiene:CONTIENE {cantidad: cantidad}]->(p)\n" + //
                                                                "SET p.stock = p.stock - cantidad",
                            Values.parameters("usuario", usuario, "nombre", nombre, "cantidad",cantidad));
                        }
                    }
                    break; // Salir del bucle si la cantidad es válida
                }

            } return true;
        }else{
            System.out.println("Error. El producto ya se encuentra en su carrito.");
            return false;
        }
    }

    public boolean eliminarProducto(Scanner sc, String usuario){
        if(existeCarrito(usuario)){
            System.out.print("Ingrese el nombre del producto que desea eliminar de su carrito: ");
            String nombre = sc.nextLine();

            //Verificar si un producto esta en el carrito
            boolean existe= existeProductoEnCarrito(nombre, usuario);

            if(existe==true){
            try(Driver driver = GraphDatabase.driver("bolt://localhost:7687")){
                try(Session session = driver.session()){
                    session.run("MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto {nombre: $nombre}) SET p.stock = p.stock + r.cantidad DELETE r",
                    Values.parameters("usuario", usuario, "nombre", nombre));
                }
            }
            return true;
            }
            else{
                System.out.println("No está el producto en su carrito.");
                return false;
            }
        }else{
            System.out.println("Aún no tiene productos en su carrito.");
            return false;
    }
    }

    public boolean existeProductoEnCarrito(String producto, String usuario){
        if(existeCarrito(usuario)){
            try (Driver driver = GraphDatabase.driver("bolt://localhost:7687")) {
                try (Session session = driver.session()) {
                    Result result = session.run(
                        "MATCH (c:Carrito {idUsuario: $usuario})-[:CONTIENE]->(p:Producto {nombre: $nombre}) RETURN p",
                        Values.parameters("usuario", usuario, "nombre", producto)
                    );
                    
                    return result.hasNext(); // Devuelve true si hay al menos un resultado
                }
            }
        }
        else{
            return false;
        }
    }

    public void verMiCarrito(String usuario) {
        double valorCarrito = 0;
        if(existeCarrito(usuario)){
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687")) {
            try (Session session = driver.session()) {
                Result result = session.run(
                    "MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto) " +
                    "RETURN p.nombre AS nombre, p.precio AS precio, p.stock AS stock, p.descripcion AS descripcion, r.cantidad AS cantidad, p.precio * r.cantidad AS valorTotal",
                    Values.parameters("usuario", usuario)
                );
                
                System.out.println("=======================");
                while (result.hasNext()) {
                    Record record = result.next();
    
                    String nombre = record.get("nombre").asString();
                    double precio = record.get("precio").asDouble();
                    int cantidad = record.get("cantidad").asInt();
                    int stock = record.get("stock").asInt();
                    double valorTotal = record.get("valorTotal").asDouble();
                    
                    System.out.println("Producto: " + nombre);
                    System.out.println("Precio unitario: " + precio);
                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Valor total: " + valorTotal);
                    System.out.println("Stock: " + stock);
                    System.out.println("=======================");
    
                    valorCarrito += valorTotal; // Sumar el valor total de cada producto al carrito
                }
            }
            
            System.out.println("IMPORTE TOTAL: " + valorCarrito);
            System.out.println("Fin del carrito.");
            
        } catch (Exception e) {
            System.err.println("Error al obtener el carrito: " + e.getMessage());
        }
    }else{
        System.out.println("Aún no tiene productos en su carrito.");
    }
    }

    public void cambiarCantidad(String usuario, Scanner sc) {
        if (existeCarrito(usuario)) {
            System.out.println("Ingrese el nombre del producto cuya cantidad desea cambiar: ");
            String nombreProducto = sc.nextLine();
            
            System.out.println("Ingrese la nueva cantidad: ");
            int nuevaCantidad = sc.nextInt();
            
            if(nuevaCantidad>0){
                try (Driver driver = GraphDatabase.driver("bolt://localhost:7687")) {
                    try (Session session = driver.session()) {
                        // Obtener la cantidad actual y el stock del producto en el carrito
                        Result result = session.run(
                            "MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto {nombre: $nombreProducto}) " +
                            "RETURN r.cantidad AS cantidad, p.stock AS stock",
                            Values.parameters("usuario", usuario, "nombreProducto", nombreProducto)
                        );
                        
                        if (result.hasNext()) {
                            Record record = result.next();
                            int cantidadActual = record.get("cantidad").asInt();
                            int stock = record.get("stock").asInt();
                            
                            // Si la nueva cantidad es mayor que la anterior
                            if (nuevaCantidad > cantidadActual) {
                                // Verificar el stock
                                if (nuevaCantidad <= stock) {
                                    // Restarle la nueva cantidad al stock
                                    int diferencia = nuevaCantidad - cantidadActual;
                                    session.run(
                                        "MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto {nombre: $nombreProducto}) " +
                                        "SET r.cantidad = $nuevaCantidad, p.stock = p.stock - $diferencia",
                                        Values.parameters("usuario", usuario, "nombreProducto", nombreProducto, "nuevaCantidad", nuevaCantidad, "diferencia", diferencia)
                                    );
                                    System.out.println("Cantidad actualizada correctamente.");
                                } else {
                                    System.out.println("No hay suficiente stock disponible.");
                                }
                            }
                            // Si la nueva cantidad es menor que la anterior
                            else if (nuevaCantidad < cantidadActual) {
                                int diferencia = cantidadActual - nuevaCantidad;
                                session.run(
                                    "MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto {nombre: $nombreProducto}) " +
                                    "SET r.cantidad = $nuevaCantidad, p.stock = p.stock + $diferencia",
                                    Values.parameters("usuario", usuario, "nombreProducto", nombreProducto, "nuevaCantidad", nuevaCantidad, "diferencia", diferencia)
                                );
                                System.out.println("Cantidad actualizada correctamente.");
                            }
                            // Si la nueva cantidad es igual a la anterior, no se hace ningún cambio
                            else {
                                System.out.println("La nueva cantidad es igual a la cantidad actual, no se realiza ningún cambio.");
                            }
                        } else {
                            System.out.println("El producto no existe en su carrito.");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al cambiar la cantidad del producto: " + e.getMessage());
                }
            } else{
                System.out.println("La cantidad debe ser mayor a 0.");
            }
        }   else {
            System.out.println("Aún no tiene productos en su carrito.");
        }
        }

        public void intercambiarProducto(String usuario, Scanner sc){
            boolean eliminar= eliminarProducto(sc, usuario);
            if(eliminar==true){
                boolean agregar= agregarProducto(sc, usuario);
                if(agregar==true){
                    System.out.println("Los productos se han intercambiado exitosamente.");
                }else{
                    System.out.println("No se pudo agregar el producto. No se completó el proceso de intercambio.");
                }
            }else{
                System.out.println("No se pudo eliminar el producto");
            }
    }

    public double calcularTotalCarrito(String usuario) {
        double valorCarrito = 0;
    
        if (existeCarrito(usuario)) {
            try (Driver driver = GraphDatabase.driver("bolt://localhost:7687")) {
                try (Session session = driver.session()) {
                    Result result = session.run(
                        "MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto) " +
                        "RETURN p.nombre AS nombre, p.precio AS precio, r.cantidad AS cantidad",
                        Values.parameters("usuario", usuario)
                    );
    
                    while (result.hasNext()) {
                        Record record = result.next();
    
                        double precio = record.get("precio").asDouble();
                        int cantidad = record.get("cantidad").asInt();
                        double valorTotalProducto = precio * cantidad;
    
                        valorCarrito += valorTotalProducto; // Sumar el valor total de cada producto al carrito
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al obtener el carrito: " + e.getMessage());
            }
        }
    
        return valorCarrito;
    }

    public void vaciarCarrito(String usuario) {
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687")) {
            try (Session session = driver.session()) {
                session.run("MATCH (c:Carrito {idUsuario: $usuario})-[r:CONTIENE]->(p:Producto) " +
                            "DELETE r",
                            Values.parameters("usuario", usuario));
            }
        }
        System.out.println("Carrito vaciado correctamente.");
    }

}
