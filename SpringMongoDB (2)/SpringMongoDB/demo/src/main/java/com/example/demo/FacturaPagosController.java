package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/operaciones")
public class FacturaPagosController {
    @Autowired
    private PedidosRepository pedidoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @PostMapping("/facturar/{pedidoId}")
    public ResponseEntity<String> facturarPedido(@PathVariable String pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            Factura factura = new Factura();
            factura.setPedidoId(pedidoId);
            factura.setMonto(pedido.getImporteTotal());
            factura.setFecha(new Date());
            // Setear el medio de pago y operador interviniente según sea necesario
            facturaRepository.save(factura);
            return ResponseEntity.ok("Pedido facturado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pagar/{facturaId}")
    public ResponseEntity<String> registrarPago(@PathVariable String facturaId, @RequestBody Pago pago) {
        Factura factura = facturaRepository.findById(facturaId).orElse(null);
        if (factura != null) {
            pago.setFacturaId(facturaId);
            pago.setFecha(new Date());
            // Guardar el pago en la base de datos
            pagoRepository.save(pago);
            return ResponseEntity.ok("Pago registrado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
