package com.example.demo;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		int texto;
		texto = null;
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("¡Bienvenido a Supermercado Aldrich!");
		System.out.println("Ingrese 1 para ingresar a su cuenta, ingrese 2 para crear una cuenta, ingrese 0 para salir");
		Scanner scanner = new Scanner(System.in);
		texto = scanner.nextInt();
			switch (texto) { // Hacer recursisvo mientras texto sea distinto de 0, 1 o 2
				case 1:
					//Ingresar a cuenta
					System.out.println("Ingrese 1 para ver el catalogo, ingrese 2 para ver su carrito, ingrese 3 para modificar el carrito, ingrese 4 para pagar facturas");
					texto = scanner.nextInt();
					switch(texto){
						case 1:
							//Mostrar Catalogo
						case 2:
							// Ver Carrito
						case 3:
							System.out.println("Ingrese 1 para agregar productos, ingrese 2 para sacar productos, ingrese 3 para cambiar productos, ingrese 4 para cambiar cantidad de un producto, ingrese 5 para generar pedido");
							texto = scanner.nextInt();
							switch(texto){ //bucle
								case 1:
									// Agregar productos
								case 2:
									// Sacar productos
								case 3:
									// Cambiar productos
								case 4:
									// Cambiar cantidad de producto
								case 5:
									// Generar pedido
								default:
									//????
							}
						case 4:
							// Pagar Facturas
						default:
							//????
					}
				case 2:
					//Crear cuenta
					scanner.close();
				case 0:
					System.out.println("Saliendo");
					break;
				default:
					System.out.print("Opcion inválida, Por favor ingrese una 1, 2 o 0");
		}
	}
}

/*
 1. Bienvenido!
	1.1 Desea ingresar a su cuenta
		1.1.1 Usuario y contraseña correcta
		1.1.2 Error en usuario o contraseña
	1.2 Crear usuario
		1.2.1 Ingrese id y cree contraseña
			1.2.1.1 Su id ya existe
			1.2.1.2 Bienvenido a la familia!
	1.3 Salir del supermercado
2. Seleccione la accion que quiera realizar
	2.1 Ver catalogo
		2.1.1 Despliega catalogo
		2.1.2 Salir
 	2.2 Ver carrito
		2.2.1 Despliega carrito
		2.2.2 Salir
 	2.3 Modificar carrito
		2.3.1 Agregar productos
		2.3.2 Sacar productos
		2.3.3 Cambiar cantidad
		2.3.4 Cambiar productos
		2.3.5 Generar Pedido
	2.4 Pagar facturas
		2.4.1 Desplegar facturas
			2.4.1.1 Elegir facturas a pagar
			2.4.1.2 Salir
 */
