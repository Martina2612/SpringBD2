package com.example.supermercado.services;

import java.time.Duration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermercado.modules.ActividadUsuario;
import com.example.supermercado.modules.Usuario;
import com.example.supermercado.repositories.ActividadUsuarioRepository;
import com.example.supermercado.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadUsuarioRepository actividadUsuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerUsuariosPorNombre(String nombre) {
        mostrarUsuarioYActividadesPorNombre(nombre);
        return usuarioRepository.findByNombre(nombre);

    }

    //Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    //Registrar usuario
    public String registrarUsuario(Scanner sc){
        
        System.out.print("Nombre de usuario: ");
		String nombreUsuario = sc.nextLine();

			
		// Validar las credenciales del usuario
		boolean usuarioValido = validarUsuario(nombreUsuario);
			
		// Mostrar un mensaje de registro exitoso o fallido
		if (usuarioValido==false) {
            System.out.print("Contraseña: ");
            String contraseña = sc.nextLine();

            System.out.print("Nombre y apellido: ");
            String nombre = sc.nextLine();

            System.out.print("Documento de identidad: ");
            String dni = sc.nextLine();

            System.out.print("Domicilio: ");
            String direccion = sc.nextLine();

            createUsuario(nombreUsuario, contraseña, nombre, dni, direccion);
			System.out.println("¡Registro exitoso!");
			} else {
			    System.out.println("Ya exite un usuario con ese nombre.");
                registrarUsuario(sc);
					}
        return nombreUsuario;
        
    }

    //Crear un nuevo usuario
    public Usuario createUsuario(String id, String contraseña, String nombre, String dni, String direccion) {
        Usuario usuario= new Usuario();
        usuario.setId(id);
        usuario.setContraseña(contraseña);
        usuario.setNombre(nombre);
        usuario.setDni(dni);
        usuario.setDireccion(direccion);
        usuario.setCategoria("LOW");
        return usuarioRepository.save(usuario);
    }

    public boolean validarUsuario(String nombreUsuario) {
        // Consultar en la base de datos si existe un usuario con las credenciales proporcionadas
        Optional<Usuario> usuario = usuarioRepository.findById(nombreUsuario);
        return usuario.isPresent();
    }

    public String iniciarSesion(Scanner sc) {
        System.out.println("Ingrese sus datos. Para salir presione -1.");
    
        String contraseña;
        String nombreUsuario;
        do {
            System.out.print("Nombre de usuario: ");
            nombreUsuario = sc.nextLine();
    
            if (nombreUsuario.equals("-1")) {
                break; // Salir si se ingresa -1
            }
    
            System.out.print("Contraseña: ");
            contraseña = sc.nextLine();
    
            if (validarUsuario(nombreUsuario)) {
                Optional<Usuario> usuario = usuarioRepository.findById(nombreUsuario);
                if (usuario.get().getContraseña().equals(contraseña)) {
                    registrarActividadUsuario(nombreUsuario);
                    System.out.println("¡Inicio de sesión exitoso!");
                    break; // Salir del ciclo
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            } else {
                System.out.println("Usuario no válido.");
            }
    
        } while (true);
    
        return nombreUsuario;
    }

    //Registrar el inicio de sesion del usuario (tiempo)
    public void registrarActividadUsuario(String usuarioId) {
        ActividadUsuario actividad = new ActividadUsuario();
        actividad.setUsuarioId(usuarioId);
        actividad.setInicioSesion(LocalDateTime.now());

        actividadUsuarioRepository.save(actividad);
    }

    //Registrar el fin de sesion del usuario (tiempo)
    public ActividadUsuario finalizarActividadUsuario(String usuarioId) {
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
    
    
    public List<ActividadUsuario> obtenerActividadesUsuario(String usuarioId) {
        return actividadUsuarioRepository.findByUsuarioId(usuarioId);
    }

    
    //Consultar la categoria del usuario segun su tiempo de actividad.
    public void obtenerCategoriaUsuario(String usuarioId) {
        try {
            List<ActividadUsuario> actividades = actividadUsuarioRepository.findByUsuarioId(usuarioId);
            
            Optional<Usuario> usuarios=usuarioRepository.findById(usuarioId);
            Usuario usuario= usuarios.get();
            
            if (actividades.isEmpty()) {
                usuario.setCategoria("LOW");
            }
            
            long duracionTotalMinutos = 0;
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime inicioHoy = LocalDateTime.of(ahora.toLocalDate(), LocalTime.MIN);
            LocalDateTime finHoy = LocalDateTime.of(ahora.toLocalDate(), LocalTime.MAX);
            
            for (ActividadUsuario actividad : actividades) {
                LocalDateTime inicio = actividad.getInicioSesion();
                LocalDateTime fin = actividad.getFinSesion() != null ? actividad.getFinSesion() : ahora;
                
                if (inicio.isAfter(inicioHoy) && fin.isBefore(finHoy)) {
                    duracionTotalMinutos += Duration.between(inicio, fin).toMinutes();
                }            
            }
            
            if (duracionTotalMinutos > 240) {
                usuario.setCategoria("TOP");
            } else if (duracionTotalMinutos > 120) {
                usuario.setCategoria("MEDIUM");
            } else {
                usuario.setCategoria("LOW");
            }

            usuarioRepository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace(); // Cualquier otro manejo de errores
        }
    }

    public void mostrarCategoria(String usuarioId){
        Optional<Usuario> usuarios=usuarioRepository.findById(usuarioId);
        Usuario usuario= usuarios.get();
        System.out.println("La categoria del usuario es: "+ usuario.getCategoria());
    }

    
    public void mostrarUsuarioYActividadesPorNombre(String nombre) {
        Optional<Usuario> usuarios = usuarioRepository.findById(nombre);
        
        usuarios.ifPresent(usuario -> {
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Direccion: " + usuario.getDireccion());
            System.out.println("Documento Identidad: " + usuario.getDni());
            System.out.println("Categoria: " + usuario.getCategoria());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("                 Actividades del Usuario:             ");
            
            List<ActividadUsuario> actividades = actividadUsuarioRepository.findByUsuarioId(usuario.getId());
            
            for (ActividadUsuario actividad : actividades) {
                System.out.println("{Inicio: " + actividad.getInicioSesion() +
                                   ", Fin: " + actividad.getFinSesion() +
                                   ", Duracion: " + calcularDuracion(actividad.getInicioSesion(), actividad.getFinSesion()) + " minutos}");
            }
            
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        });
    }

    private long calcularDuracion(LocalDateTime inicio, LocalDateTime fin) {
        if (fin != null) {
            return Duration.between(inicio, fin).toMinutes();
        } else {
            return 0; // Si no hay tiempo de finalización, la duración es cero o puede ser otra valor predeterminado.
        }
    }


    public void mostrarDatosUsuarios(){
        List<Usuario> usuarios=usuarioRepository.findAll();
        System.out.println("----------------------------------------------------------");
        System.out.println("|                  REGISTRO DE USUARIOS                   | ");
        System.out.println("----------------------------------------------------------");
        for(Usuario usuario : usuarios){
            System.out.println("Nombre: "+ usuario.getNombre());
            System.out.println("DNI: "+ usuario.getDni());
            System.out.println("Direccion: "+ usuario.getDireccion());
            System.out.println("Usuario: "+ usuario.getUsuario());
            System.out.println("Contraseña: "+ usuario.getContraseña());
            System.out.println("Categoria: "+ usuario.getCategoria());
            System.out.println("----------------------------------------------------------");
        }
    }

}
