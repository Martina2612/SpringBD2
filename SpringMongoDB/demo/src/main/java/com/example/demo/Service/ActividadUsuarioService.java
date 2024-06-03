package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ActividadUsuario;
import com.example.demo.Repository.ActividadUsuarioRepository;

@Service
public class ActividadUsuarioService {
    @Autowired
    private ActividadUsuarioRepository actividadUsuarioRepository;
    
    public void registrarActividadUsuario(String usuarioId) {
        ActividadUsuario actividad = new ActividadUsuario();
        actividad.setUsuarioId(usuarioId);
        actividad.setInicioSesion(LocalDateTime.now());
        
        actividadUsuarioRepository.save(actividad);
    }
    
    public String categorizarUsuario(String usuarioId) {
        List<ActividadUsuario> actividades = actividadUsuarioRepository.findByUsuarioId(usuarioId);
        long duracionTotalMinutos = actividades.stream()
                .mapToLong(actividad -> Duration.between(actividad.getInicioSesion(), actividad.getFinSesion()).toMinutes())
                .sum();
        
        if (duracionTotalMinutos > 240) {
            return "TOP";
        } else if (duracionTotalMinutos > 120) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }
}
