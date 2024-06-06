package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private CatalogoCambioRepository catalogoCambioRepository;

    //Endpoint para crear un nuevo producto
    @PostMapping("/productos")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    //Endpoint para obtener un producto por nombre
    @GetMapping("/productos/{nombre}")
    public Producto obtenerProducto(@PathVariable String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    // Endpoint para obtener el precio actual de un producto
    @GetMapping("/productos/{id}/precio")
    public double obtenerPrecioActual(@PathVariable Long id) {
        Optional<PrecioProducto> precioOptional = PrecioRepository.findByProductoIdOrderByFechaDesc(id)
        .stream()
        .findFirst();

        if (precioOptional.isPresent()) {
        return precioOptional.get().getPrecio();
        } else {
        // Manejar el caso en que no se encuentre ningún precio para el producto
        throw new RuntimeException("No se encontró ningún precio para el producto con ID " + id);
        }
    }

    // Endpoint para obtener el historial de precios de un producto
    @GetMapping("/productos/{id}/historialPrecios")
    public List<PrecioProducto> obtenerHistorialPrecios(@PathVariable Long id) {
        return PrecioRepository.findByProductoIdOrderByFechaDesc(id);
    }

    // Endpoint para registrar un cambio en el catálogo
    @PostMapping("/productos/{id}/cambios")
    public void registrarCambioProducto(@PathVariable Long id, @RequestBody CatalogoCambio cambio) {
        // Validar la información del cambio
        if (cambio.getIdProducto() == null || cambio.getCampoModificado() == null ||
            cambio.getValorAnterior() == null || cambio.getValorNuevo() == null ||
            cambio.getOperador() == null) {
            throw new IllegalArgumentException("Falta información para registrar el cambio");
        }

        // Guardar el cambio en la base de datos
        catalogoCambioRepository.save(cambio);

    }

}
