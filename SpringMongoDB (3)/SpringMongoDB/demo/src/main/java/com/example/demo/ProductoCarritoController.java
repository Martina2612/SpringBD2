package com.example.demo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping()
public class ProductoCarritoController {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    private Map<String, CarritoMemento> historialCarrito = new HashMap<>();

    //Endpoint para agregar un producto al carrito
    @PostMapping("/carritos/{idUsuario}/agregar/{nombreProducto}/{cantidad}")
    public void agregarProductoACarrito(@PathVariable String idUsuario, @PathVariable String nombreProducto, @PathVariable int cantidad) {
        Carrito carrito= carritoRepository.findByUsuarioId(idUsuario);

        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuarioId(idUsuario);
            carritoRepository.save(carrito);
            guardarEstadoCarrito(carrito);
        }

        Producto producto= productoRepository.findByNombre(nombreProducto);

        if(carrito.getUsuarioId().equals(idUsuario)){
            if(producto.getStock()>=cantidad){ //poner si es mayor a 0
            carrito.agregarProducto(producto, cantidad);
            carritoRepository.save(carrito);
            producto.setStock(producto.getStock()-cantidad);
            productoRepository.save(producto);}
        }

    }

    // Endpoint para obtener los productos del carrito de un usuario
    @GetMapping("/carritos/{idUsuario}/productos")
    public ResponseEntity<Object> obtenerProductosCarrito(@PathVariable String idUsuario) {
        Carrito carrito = carritoRepository.findByUsuarioId(idUsuario);

        if (carrito != null) {
            List<ProductoCantidad> productosConCantidad = carrito.getProductosConCantidad();
            return ResponseEntity.ok(productosConCantidad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //Endpoint para eliminar un producto del carrito
    @PostMapping("/carritos/{idUsuario}/eliminar/{nombreProducto}")
    
    public void eliminarProductoDeCarrito(String idUsuario, String nombreProducto) {
        Carrito carrito = carritoRepository.findByUsuarioId(idUsuario);

        if (carrito != null) {
            Producto producto = productoRepository.findByNombre(nombreProducto);

            if (carrito.getProductosConCantidad().stream()
                .anyMatch(pc -> pc.getProducto().getNombre().equals(nombreProducto))) {
                // Elimina la relación entre producto y carrito
                carrito.getProductosConCantidad().removeIf(pc -> pc.getProducto().equals(producto));

                // Actualiza el stock del producto
                producto.setStock(producto.getStock() + 
                    carrito.getProductosConCantidad().stream()
                            .filter(pc -> pc.getProducto().getNombre().equals(nombreProducto))
                            .findFirst()
                            .orElse(new ProductoCantidad()).getCantidad());
                
                carrito.eliminarProducto(producto);

                // Guarda los cambios
                carritoRepository.save(carrito);
                productoRepository.save(producto);
            }
        }
    }

    //Endpoint para cambiar la cantidad de un producto
    /* 
    @PutMapping("/carritos/{idUsuario}/productos/{nombreProducto}/{cantidadNueva}")
    public ResponseEntity<Object> modificarCantidadProductoCarrito(
    @PathVariable String idUsuario,
    @PathVariable String nombreProducto,
    @PathVariable Integer cantidadNueva // Change int to Integer
) {
    Carrito carrito = carritoRepository.findByUsuarioId(idUsuario);

    if (carrito != null) {
        Producto producto = productoRepository.findByNombre(nombreProducto);

        if (producto != null) {
            int cantidadAnterior = carrito.getCantidadProducto(producto);

            if (cantidadAnterior > 0 && cantidadNueva != null && cantidadNueva >= 0 && producto.getStock() >= cantidadNueva) {
                carrito.modificarCantidadProducto(producto, cantidadNueva);
                producto.setStock(producto.getStock() - (cantidadNueva - cantidadAnterior));

                carritoRepository.save(carrito);
                productoRepository.save(producto);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    } else {
        return ResponseEntity.notFound().build();
    }
}*/


@DeleteMapping("/carritos/{idUsuario}/eliminar/{nombreProducto}")
    public ResponseEntity<String> eliminarProductoDelCarrito(@PathVariable String idUsuario, @PathVariable String nombreProducto) {
        Carrito carrito = carritoRepository.findByUsuarioId(idUsuario);
        Producto producto = productoRepository.findByNombre(nombreProducto);

        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (!carrito.getUsuarioId().equals(idUsuario)) {
            return ResponseEntity.badRequest().body("El usuario no tiene este producto en el carrito");
        }

        // Eliminar la relación CONTIENE entre el nodo producto y el nodo carrito
        carrito.eliminarProducto(producto);
        carritoRepository.save(carrito);
        guardarEstadoCarrito(carrito); // Guardar el estado del carrito
        
        // Modificar el stock del producto
        int cantidadEliminada = carrito.getProductosConCantidad().stream()
                                .filter(pc -> pc.getProducto().equals(producto))
                                .mapToInt(ProductoCantidad::getCantidad)
                                .sum();
        producto.setStock(producto.getStock() + cantidadEliminada);
        productoRepository.save(producto);

        return ResponseEntity.ok("Producto eliminado del carrito exitosamente");
    }

    private void guardarEstadoCarrito(Carrito carrito) {
        historialCarrito.put(carrito.getUsuarioId(), new CarritoMemento(carrito));
    }

    @PostMapping("/carritos/{idUsuario}/restaurar")
    public ResponseEntity<String> restaurarEstadoCarrito(@PathVariable String idUsuario) {
        if (historialCarrito.containsKey(idUsuario)) {
            CarritoMemento memento = historialCarrito.get(idUsuario);
            Carrito carrito = carritoRepository.findByUsuarioId(idUsuario);
            if (carrito != null) {
                carrito.restaurarEstado(memento);
                // Se guarda el estado restaurado en la base de datos
                return ResponseEntity.ok("Estado del carrito restaurado correctamente.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    

}
    

