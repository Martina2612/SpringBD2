package com.example.supermercado.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermercado.modules.Carrito;
import com.example.supermercado.modules.Factura;
import com.example.supermercado.modules.Pedido;
import com.example.supermercado.modules.Usuario;
import com.example.supermercado.repositories.CarritoRepository;
import com.example.supermercado.repositories.FacturaRepository;
import com.example.supermercado.repositories.PedidosRepository;
import com.example.supermercado.repositories.UsuarioRepository;

@Service
public class PedidoFacturaService {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    CarritoService carritoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PedidosRepository pedidosRepository;

    @Autowired 
    FacturaRepository facturaRepository;

    @Autowired
    RegistroOperacionesService registroOperacionesService;

    //METODOS PARA GENERAR LAS FACTURAS

    public void pagarFactura(Factura factura, Scanner sc, double monto, String usuario){
        boolean medioPagoValido = false;

        while (!medioPagoValido) {
            System.out.println("Elija el medio de pago: ");
            System.out.println("1. Efectivo");
            System.out.println("2. Débito");
            System.out.println("3. Crédito");
            System.out.println("4. Cta. Cte.");

            String medio = sc.nextLine();

            switch (medio) {
                case "1":
                    factura.setMedioPago("Efectivo");
                    medioPagoValido = true;
                    break;
                case "2":
                    factura.setMedioPago("Débito");
                    medioPagoValido = true;
                    break;
                case "3":
                    factura.setMedioPago("Crédito");
                    medioPagoValido = true;
                    break;
                case "4":
                    factura.setMedioPago("Cta. Cte.");
                    medioPagoValido = true;
                    break;
                default:
                    System.out.println("Opción incorrecta. Por favor, seleccione un medio de pago válido.");
            }
        }

        factura.setFechaPago(LocalDate.now());
        factura.setPagado(true);
    }

    public void elegirPagoFacturas(Scanner sc, String usuario) {
        List<Factura> facturas= facturaRepository.findByIdUsuario(usuario);

        boolean continuarPagando = true;

        while (continuarPagando) {
            // Mostrar las facturas disponibles para pagar
            System.out.println("Facturas disponibles para pagar:");
            for (Factura factura : facturas) {
                if(factura.getPagado()==false){
                    System.out.println("ID: " + factura.getIdFactura() + " - Importe Total: " + factura.getImporte());};
            }

            // Pedir al usuario que ingrese el ID de la factura que desea pagar (0 para salir)
            System.out.print("Ingrese el ID de la factura que desea pagar (0 para salir): ");
            String facturaId = sc.nextLine();

            // Verificar si el usuario quiere salir
            if (facturaId.equals("0")) {
                continuarPagando = false;
                continue;
            }

            // Buscar la factura en la lista por ID
            Factura facturaSeleccionada = null;
            for (Factura factura : facturas) {
                if (factura.getIdFactura().equals(facturaId) && factura.getPagado()==false) {
                    facturaSeleccionada = factura;
                    break;
                }
            }

            if (facturaSeleccionada == null) {
                System.out.println("Error en la factura ingresada.");
                continue;
            }

            // Mostrar detalles de la factura seleccionada
            System.out.println("Factura seleccionada: ID " + facturaSeleccionada.getIdFactura() +
                               " - Importe Total: " + facturaSeleccionada.getImporte());

            // Pedir al usuario que ingrese el monto a pagar
            System.out.print("Ingrese el monto a pagar: ");
            double monto = Double.parseDouble(sc.nextLine());

            // Validar el monto ingresado y procesar el pago si es correcto
            if (monto == facturaSeleccionada.getImporte()) {
                // Realizar el pago (llamar a un método para procesar el pago)
                pagarFactura(facturaSeleccionada, sc,monto, usuario);
                String operadorInterviniente=null;
                registroOperacionesService.registrarOperacion("Pago", facturaSeleccionada.getMedioPago(), operadorInterviniente, LocalDateTime.now(),monto,usuario);
                System.out.println("Pago realizado correctamente.");
            } else {
                System.out.println("El monto ingresado es distinto al importe total de la factura.");
            }
        }

        System.out.println("Proceso de pago de facturas finalizado.");
    }

    public void generarFactura(Pedido pedido, String usuario){

        Factura factura  = new Factura();
        factura.setIdPedido(pedido.getIdPedido());
        factura.setIdUsuario(pedido.getUsuario().getId());
        factura.setImporte(pedido.getImporteFinalTotal());
        facturaRepository.save(factura);
        registroOperacionesService.registrarOperacion("Facturación", factura.getMedioPago(), null, LocalDateTime.now(),factura.getImporte(),usuario);

    }


    //METODOS PARA GENERAR EL PEDIDO

    public Pedido calcularPedido(String usuarioId, Scanner sc){
       Optional<Usuario> usuarios=usuarioRepository.findById(usuarioId);
       Usuario usuario= usuarios.get(); 

       Carrito carrito=carritoRepository.findByIdUsuario(usuarioId);

       boolean existe= carritoService.existeCarrito(usuarioId);
       while(true){
        if(existe==true){
            System.out.println("Seleccione su condicion de IVA.");
            System.out.println("1.Exento");
            System.out.println("2.Gravado");
            System.out.print("Opción: ");
            String condicion=sc.nextLine();

            double importeCarrito=carritoService.calcularTotalCarrito(usuarioId);
            double importeFinal=importeCarrito;

            //Se aplica un aumento del 21% si el cliente se encuentra Gravado en IVA
            if(condicion=="2"){
                condicion="Gravado";
                importeFinal+=calcularIva(importeFinal);
            }else{
                condicion="Exento";
            }

            //Se calcula el descuento
            importeFinal-=calcularDescuento(usuario, importeFinal);


            Pedido pedido= new Pedido();
            pedido.setCarrito(carrito);
            pedido.setUsuario(usuario);
            pedido.setCondicionIVA(condicion);
            pedido.setImporteTotal(importeCarrito);
            pedido.setImporteFinalTotal(importeFinal);
            pedidosRepository.save(pedido);

            return pedido;
         }
      }
    }   

    public double calcularIva(double importe){
        //Se calcula un IVA del 21% y en generarPedido se lo suma
        return (importe*0.21);
    }

    public double calcularDescuento(Usuario usuario, double importe){
        if (usuario.getCategoria()=="LOW"){
            return (importe*0.05);
        }

        if(usuario.getCategoria()=="MEDIUM"){
            return (importe*0.10);
        }

        else{
            return (importe*0.20);
        }
    }
}
