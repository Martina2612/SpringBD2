package com.example.demo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String facturaId;
    private double monto;
    private Date fecha;
    private String medioPago;
    private String operadorInterviniente;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacturaId() {
        return this.facturaId;
    }

    public void setFacturaId(String facturaId) {
        this.facturaId = facturaId;
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
