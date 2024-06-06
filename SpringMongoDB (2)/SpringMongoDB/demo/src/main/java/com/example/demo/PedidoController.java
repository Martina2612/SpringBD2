package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidosRepository pedidoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/comprar/{idUsuario}")
    public ResponseEntity<String> convertirCarritoEnPedido(@PathVariable String idUsuario) {

        Carrito carrito = carritoRepository.findByUsuarioId(idUsuario);
        Optional<Usuario> usuario= usuarioRepository.findById(idUsuario);

        // Calcular importe total del carrito
        double importeTotal = calcularImporteTotal(carrito);

        // Calcular descuento
        double descuento = calcularDescuento(carrito);

        // Calcular impuestos
        double impuestos = calcularImpuestos(carrito);

        // Crear un objeto Pedido con la información proporcionada
        Pedido pedido = new Pedido();
        pedido.setCarrito(carrito);
        pedido.setUsuario(usuario);
        pedido.setImporteTotal(importeTotal);
        pedido.setDescuento(descuento);
        pedido.setImpuestos(impuestos);

        // Guardar el pedido en la base de datos
        pedidoRepository.save(pedido);

        return ResponseEntity.ok("Pedido creado exitosamente.");
    }

    private double calcularImporteTotal(Carrito carrito) {
        double importeTotal = 0.0;
        for (ProductoCantidad productoCantidad : carrito.getProductos()) {
            importeTotal += productoCantidad.getProducto().getPrecio() * productoCantidad.getCantidad();
        }
        return importeTotal;
    }

    private double calcularDescuento(Carrito carrito) {
        // Aplicar un descuento del 10% si el importe total es mayor 100
        double importeTotal = calcularImporteTotal(carrito);
        if (importeTotal > 100) {
            return importeTotal * 0.1;
        } else {
            return 0.0;
        }
    }

    private double calcularImpuestos(Carrito carrito) {
        // Aplicar un impuesto del 15% sobre el importe total
        double importeTotal = calcularImporteTotal(carrito);
        return importeTotal * 0.15;
    }

}
