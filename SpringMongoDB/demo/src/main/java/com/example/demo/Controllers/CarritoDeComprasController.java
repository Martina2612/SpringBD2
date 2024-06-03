package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.CarritoDeCompras;
import com.example.demo.Producto;
import com.example.demo.Repository.CarritoDeComprasRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.demo.Service.CarritoDeComprasService;

@RestController
@RequestMapping("/carrito")
public class CarritoDeComprasController {

    @Autowired
    private CarritoDeComprasService carritoDeComprasService;

    @Autowired
    private CarritoDeComprasRepository carritoDeComprasRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoDeCompras> obtenerCarrito(@PathVariable String usuarioId) {
        CarritoDeCompras carrito = carritoDeComprasRepository.findByUsuarioId(usuarioId);
        if (carrito != null) {
            return ResponseEntity.ok(carrito);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{usuarioId}/agregar/{nombreProducto}/{cantidadAComprar}")
    public void agregarProductoAlCarrito(@PathVariable String usuarioId, 
                                         @PathVariable String nombreProducto, 
                                         @PathVariable int cantidadAComprar) {
        // Busca el producto por nombre utilizando el repositorio de productos
        List<Producto> productos = productoRepository.findByNombre(nombreProducto);
        if (!productos.isEmpty()) {
            // Suponiendo que solo hay un producto con el mismo nombre
            Producto producto = productos.get(0);
            carritoDeComprasService.agregarProductoAlCarrito(usuarioId, producto, cantidadAComprar);
        } else {
            // Manejar el caso en que el producto no existe
        }
    }

    @DeleteMapping("/{usuarioId}/eliminar/{nombreProducto}")
    public void eliminarProductoDelCarrito(@PathVariable String usuarioId, @PathVariable String nombreProducto) {
        carritoDeComprasService.eliminarProductoDelCarrito(usuarioId, nombreProducto);
    }

    @PutMapping("/{usuarioId}/cambiarCantidad/{nombreProducto}")
    public void cambiarCantidadProductoEnCarrito(@PathVariable String usuarioId, @PathVariable String nombreProducto, @RequestParam int nuevaCantidad) {
        carritoDeComprasService.cambiarCantidadProductoEnCarrito(usuarioId, nombreProducto, nuevaCantidad);
    }
}
