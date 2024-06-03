package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.CarritoDeCompras;
import com.example.demo.Contiene;
import com.example.demo.Producto;
import com.example.demo.Repository.CarritoDeComprasRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarritoDeComprasService {
    @Autowired
    private CarritoDeComprasRepository carritoDeComprasRepository;

    public void agregarProductoAlCarrito(String usuarioId, Producto producto, int cantidad) {
        CarritoDeCompras carrito = carritoDeComprasRepository.findByUsuarioId(usuarioId);
        if (carrito != null) {
            if (carrito.getContiene() == null) {
                carrito.setContiene(new ArrayList<>());
            }
            // Verificar si el producto ya está en el carrito
            Optional<Contiene> contieneOptional = carrito.getContiene().stream()
                    .filter(contiene -> contiene.getProducto().getId().equals(producto.getId()))
                    .findFirst();
    
            if (contieneOptional.isPresent()) {
                // Si el producto ya está en el carrito, actualizar la cantidad
                Contiene contiene = contieneOptional.get();
                contiene.setCantidad(contiene.getCantidad() + cantidad);
            } else {
                // Si el producto no está en el carrito, agregarlo con la cantidad especificada
                carrito.getContiene().add(new Contiene(producto, cantidad));
            }
    
            carritoDeComprasRepository.save(carrito);
        }
    }
    
    public void eliminarProductoDelCarrito(String usuarioId, String productoId) {
        CarritoDeCompras carrito = carritoDeComprasRepository.findByUsuarioId(usuarioId);
        if (carrito != null) {
            carrito.getContiene().removeIf(contiene -> contiene.getProducto().getId().equals(productoId));
            carritoDeComprasRepository.save(carrito);
        }
    }
    
    public void cambiarCantidadProductoEnCarrito(String usuarioId, String productoId, int nuevaCantidad) {
        CarritoDeCompras carrito = carritoDeComprasRepository.findByUsuarioId(usuarioId);
        if (carrito != null) {
            Optional<Contiene> contieneOptional = carrito.getContiene().stream()
                    .filter(contiene -> contiene.getProducto().getId().equals(productoId))
                    .findFirst();
    
            if (contieneOptional.isPresent()) {
                Contiene contiene = contieneOptional.get();
                contiene.setCantidad(nuevaCantidad);
                carritoDeComprasRepository.save(carrito);
            }
        }
}
}
