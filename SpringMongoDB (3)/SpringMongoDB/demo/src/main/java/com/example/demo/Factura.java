package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "facturas")

public class Factura {
    @Id
    private String id;
    private String pedidoId; //Referencia al pedido asociado
    private double monto;
    private Date fecha;
    private String medioPago;
    private String operadorInterviniente; 
    
    //Hace falta los productos???

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPedidoId() {
        return this.pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public double getMonto() {
        return this.monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return this.medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getOperadorInterviniente() {
        return this.operadorInterviniente;
    }

    public void setOperadorInterviniente(String operadorInterviniente) {
        this.operadorInterviniente = operadorInterviniente;
    }

}
