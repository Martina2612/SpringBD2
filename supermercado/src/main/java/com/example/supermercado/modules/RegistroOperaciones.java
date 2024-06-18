package com.example.supermercado.modules;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "registrosOperaciones")
public class RegistroOperaciones {

    @Id
    private String id;

    private String tipoOperacion;
    private String medioPago;
    private String operadorInterviniente;
    private LocalDateTime fechaHora;
    private double monto;
    private String usuario;


    public RegistroOperaciones() {
    }

    public RegistroOperaciones(String id, String tipoOperacion, String medioPago, String operadorInterviniente, LocalDateTime fechaHora, double monto, String usuario) {
        this.id = id;
        this.tipoOperacion = tipoOperacion;
        this.medioPago = medioPago;
        this.operadorInterviniente = operadorInterviniente;
        this.fechaHora = fechaHora;
        this.monto = monto;
        this.usuario = usuario;
    }
    

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getOperadorInterviniente() {
        return operadorInterviniente;
    }

    public void setOperadorInterviniente(String operadorInterviniente) {
        this.operadorInterviniente = operadorInterviniente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }


    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    @Override
    public String toString() {
        return "RegistroOperacion{" +
                "id='" + id + '\'' +
                ", tipoOperacion='" + tipoOperacion + '\'' +
                ", medioPago='" + medioPago + '\'' +
                ", operadorInterviniente='" + operadorInterviniente + '\'' +
                ", fechaHora=" + fechaHora +
                ", monto=" + monto +
                '}';
    }
}
