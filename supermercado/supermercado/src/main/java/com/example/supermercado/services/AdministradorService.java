package com.example.supermercado.services;


import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermercado.modules.Administrador;

import com.example.supermercado.repositories.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository adminRepository;

    public void createAdministrador(String email, String contraseña, String nombre, String numOperador){
        Administrador admin= new Administrador();
        admin.setEmail(email);
        admin.setContraseña(contraseña);
        admin.setNombre(nombre);
        admin.setNroOperador(numOperador);
        adminRepository.save(admin);
    }


    public String registrarAdmi(Scanner scanner){
        System.out.println("---------------------------------------------------------------------------------");
	            
	    System.out.print("Introduce tu email: ");
	    String email = scanner.nextLine();


        if(existeAdmi(email)==false){
            System.out.print("Introduce tu contraseña: ");
	        String contraseña = scanner.nextLine();

            System.out.print("Introduce tu nombre: ");
	        String nombre = scanner.nextLine();

            System.out.print("Introduce tu numero de operador: ");
	        String numOperador = scanner.nextLine();

            createAdministrador(email, contraseña, nombre, numOperador);

        } else{
            System.out.println("Ya existe un usuario con ese email.");
            registrarAdmi(scanner);
        }
	            
	    return email;
	            

}


    public String iniciarSesionAdmi(Scanner sc){
        System.out.println("Ingrese sus datos. Para salir presione -1.");
    
        String contraseña;
        String email;
        do {
            System.out.print("Email: ");
            email = sc.nextLine();
    
            if (email.equals("-1")) {
                break; // Salir si se ingresa -1
            }
    
            System.out.print("Contraseña: ");
            contraseña = sc.nextLine();
    
            if (existeAdmi(email)==true) {
                Optional<Administrador> admin= adminRepository.findByEmail(email);
                if (admin.get().getContraseña().equals(contraseña)) {
                    System.out.println("¡Inicio de sesión exitoso!");
                    break; // Salir del ciclo
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            } else {
                System.out.println("Email no válido.");
            }
    
        } while (true);
    
        return email;


    }

    public boolean existeAdmi(String email){

        Optional<Administrador> admin= adminRepository.findByEmail(email);
        return admin.isPresent();
    }



}
