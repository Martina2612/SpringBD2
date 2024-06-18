package com.example.supermercado.modules;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection="factura")
public class Factura {
    @Id
    private String idFactura;
    private String idPedido;
    private String idUsuario;
    private String medioPago;
    private double importe;
    private boolean pagado;
    private String operador;
    private LocalDate fechaPago;


    public String getIdFactura() {
        return this.idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public String getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMedioPago() {
        return this.medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public boolean isPagado() {
        return this.pagado;
    }

    public boolean getPagado() {
        return this.pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getOperador() {
        return this.operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public LocalDate getFechaPago() {
        return this.fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }


    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }


    public Factura() {
    }


    public Factura(String idFactura, String idPedido, String idUsuario, String medioPago, double importe, boolean pagado, String operador, LocalDate fechaPago) {
        this.idFactura = idFactura;
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.medioPago = medioPago;
        this.importe = importe;
        this.pagado = pagado;
        this.operador = operador;
        this.fechaPago = fechaPago;
    }



    
}
