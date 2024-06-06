package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //Servicio para gestionar la sesion del usuario
public class UsuarioService {
    @Autowired
	
	private UsuarioRepository usuarioRepository;
	
	public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

   /*  public Usuario obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }*/
}
