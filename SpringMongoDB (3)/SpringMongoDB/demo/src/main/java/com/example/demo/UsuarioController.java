package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadUsuarioRepository actividadUsuarioRepository;


    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping("/{nombre}")
    public List<Usuario> obtenerUsuariosPorNombre(@PathVariable String nombre) {
        mostrarUsuarioYActividadesPorNombre(nombre);
        return usuarioRepository.findByNombre(nombre);

    }

    //Endpoint para obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    //Endpoint para crear un nuevo usuario
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //Endpoint para registrar el inicio de sesion del usuario (tiempo)
     @PostMapping("/{usuarioId}/actividad")
    public ActividadUsuario registrarActividadUsuario(@PathVariable String usuarioId) {
        ActividadUsuario actividad = new ActividadUsuario();
        actividad.setUsuarioId(usuarioId);
        actividad.setInicioSesion(LocalDateTime.now());
       
        return actividadUsuarioRepository.save(actividad);
    }

    //Endpoint para registrar el fin de sesion del usuario (tiempo)
    @PostMapping("/{usuarioId}/actividad/fin")
    public ActividadUsuario finalizarActividadUsuario(@PathVariable String usuarioId) {
        // Obtener la última actividad de usuario sin tiempo de finalización
        ActividadUsuario actividad = actividadUsuarioRepository.findTopByUsuarioIdOrderByInicioSesionDesc(usuarioId);
        
        if (actividad != null) {
            // Establecer el tiempo de finalización de la actividad
            actividad.setFinSesion(LocalDateTime.now());
            
            // Guardar la actividad de usuario con el tiempo de finalización establecido
            return actividadUsuarioRepository.save(actividad);
        } else {
            // Si no hay actividad de usuario sin tiempo de finalización, no se puede finalizar ninguna actividad
            throw new RuntimeException("No se encontró ninguna actividad de usuario para finalizar.");
        }
    }
    
    @GetMapping("/{usuarioId}/actividades")
    public List<ActividadUsuario> obtenerActividadesUsuario(@PathVariable String usuarioId) {
        return actividadUsuarioRepository.findByUsuarioId(usuarioId);
    }

    @GetMapping("/{usuarioId}/categoria")
    public String obtenerCategoriaUsuario(@PathVariable String usuarioId) {
        try {
            List<ActividadUsuario> actividades = actividadUsuarioRepository.findByUsuarioId(usuarioId);
            
            if (actividades.isEmpty()) {
                return "LOW";
            }
            
            long duracionTotalMinutos = 0;
            LocalDateTime ahora = LocalDateTime.now();
            
            for (ActividadUsuario actividad : actividades) {
                LocalDateTime inicio = actividad.getInicioSesion();
                LocalDateTime fin = actividad.getFinSesion() != null ? actividad.getFinSesion() : ahora;
                duracionTotalMinutos += Duration.between(inicio, fin).toMinutes();
            }
            
            if (duracionTotalMinutos > 240) {
                mostrarCategoria("TOP");
                return "TOP";
            } else if (duracionTotalMinutos > 120) {
                mostrarCategoria("MEDIUM");
                return "MEDIUM";
            } else {
                mostrarCategoria("LOW");
                return "LOW";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR"; // Cualquier otro manejo de errores
        }
    }

    public void mostrarCategoria(String categoria){
        System.out.println("La categoria del usuario es: "+ categoria);
    }

    
    public void mostrarUsuarioYActividadesPorNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioRepository.findByNombre(nombre);
        
        for (Usuario usuario : usuarios) {
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Direccion: " + usuario.getDireccion());
            System.out.println("Documento Identidad: " + usuario.getDocumentoIdentidad());
            System.out.println("Actividades:");
            
            List<ActividadUsuario> actividades = actividadUsuarioRepository.findByUsuarioId(usuario.getId());
            
            for (ActividadUsuario actividad : actividades) {
                System.out.println("{inicio: " + actividad.getInicioSesion() +
                                   ", fin: " + actividad.getFinSesion() +
                                   ", duracion: " + calcularDuracion(actividad.getInicioSesion(), actividad.getFinSesion()) + " minutos}");
            }
            
            System.out.println("-----------------------------------");
        }
    }

    private long calcularDuracion(LocalDateTime inicio, LocalDateTime fin) {
        if (fin != null) {
            return Duration.between(inicio, fin).toMinutes();
        } else {
            return 0; // Si no hay tiempo de finalización, la duración es cero o puede ser otra valor predeterminado.
        }
    }


}