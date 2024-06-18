package com.example.supermercado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermercado.modules.RegistroOperaciones;
import com.example.supermercado.repositories.RegistroOperacionesRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroOperacionesService {

    @Autowired
    private final RegistroOperacionesRepository registroOperacionRepository;

    public RegistroOperacionesService(RegistroOperacionesRepository registroOperacionRepository) {
        this.registroOperacionRepository = registroOperacionRepository;
    }

    public void registrarOperacion(String tipoOperacion, String medioPago, String operadorInterviniente, LocalDateTime fecha,double monto, String usuario) {
        RegistroOperaciones registro = new RegistroOperaciones();
        registro.setTipoOperacion(tipoOperacion);
        registro.setMedioPago(medioPago);
        registro.setOperadorInterviniente(operadorInterviniente);
        registro.setFechaHora(fecha);
        registro.setUsuario(usuario);
        registroOperacionRepository.save(registro);
    }

    public void mostrarRegistroOperaciones() {
        // Obtener todos los registros de operaciones
        List<RegistroOperaciones> registros = registroOperacionRepository.findAll();

        if (registros.isEmpty()) {
            System.out.println("No hay registros de operaciones.");
        } else {
            System.out.println("Registro de Operaciones:");
            for (RegistroOperaciones registro : registros) {
                System.out.println(registro);
            }
        }
    }
}
