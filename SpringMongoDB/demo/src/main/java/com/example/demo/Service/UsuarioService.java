package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Usuario;
import com.example.demo.Repository.UsuarioRepository;

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
