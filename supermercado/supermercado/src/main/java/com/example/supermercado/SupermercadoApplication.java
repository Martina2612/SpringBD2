package com.example.supermercado;

import com.example.supermercado.services.AdministradorService;
import com.example.supermercado.services.CarritoService;
import com.example.supermercado.services.ProductoService;
import com.example.supermercado.services.UsuarioService;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SupermercadoApplication {


	//SECCION PRINCIPAL
	public static void principal(ConfigurableApplicationContext context, Scanner sc) {
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Bienvenido. Por favor elija una opcion.");
		String num;
		do{
			System.out.println("1. Administrador");
			System.out.println("2. Usuario");
			System.out.println("3. Salir.");
			System.out.print("Opción: ");
			num = sc.nextLine();

			switch(num){
				case "1":
					vistaPrincipalAdmi(context, sc);
					break;
				case "2":
					vistaPrincipalUsuario(context, sc);
					break;
				case "3":
					break;	
			}
		} while(!num.equals("3"));

		sc.close();
		context.close();

	}


	//SECCION ADMINISTRADOR
	public static void vistaPrincipalAdmi(ConfigurableApplicationContext context, Scanner sc){
		AdministradorService adminService = context.getBean(AdministradorService.class);
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Bienvenido Administrador. Por favor elija una opcion.");
		String num;
		do{
			System.out.println("1. Iniciar Sesión");
			System.out.println("2. Registrarse");
			System.out.println("3. Volver al menú principal.");
			System.out.print("Opción: ");
			num = sc.nextLine();
			String respuesta;
			switch (num){
				case "1":
				respuesta=adminService.iniciarSesionAdmi(sc);
				if(respuesta.equals("-1")){
					break;}
					else{
						mostrarMenuAdmin(context, sc, respuesta);
					}
				break;
				case "2":
					adminService.registrarAdmi(sc);
					vistaPrincipalAdmi(context, sc);
					break;
				case "3":
					principal(context, sc);
					break;
			}
		} while(!num.equals("1") && !num.equals("2") && !num.equals("3"));
	}


	public static void mostrarMenuAdmin(ConfigurableApplicationContext context, Scanner sc, String administrador){
		ProductoService productService=context.getBean(ProductoService.class);
		UsuarioService usuarioService=context.getBean(UsuarioService.class);
		String opcion;

		do{System.out.println("1. Agregar Producto.");
		System.out.println("2. Modificar Producto.");
		System.out.println("3. Ver registro de modificaciones.");
		System.out.println("4. Ver catálogo de productos. ");
		System.out.println("5. Eliminar producto del catalogo. ");
		System.out.println("6. Ver registro de usuarios. ");
		System.out.println("7. Volver Atras <---");
		
		System.out.print("Opción: ");

		opcion= sc.nextLine();

		switch(opcion){
			case "1":
				//Metodo para agregar o crear un producto (nodo)
				productService.crearProducto(sc);
				break;
			case "2":
				//Método para modificar los datos de un producto
				productService.modificaciones(sc, administrador);
				break;
			case "3":
				//Metodo para poder ver un registro de todos los cambios en el catálogo
				productService.mostrarRegistroModificaciones();
				break;
			case "4":
				//Método para ver el catálogo de productos
				productService.mostrarCatalogo();
				break;
			case "5":
				//Método para eliminar un producto del catalogo
				productService.eliminarProducto(sc);
				break;
			case "6":
				//Método para ver los usuarios registrados 
				usuarioService.mostrarDatosUsuarios();
				break;
			case "7":
				//Método para volver al menu principal
				vistaPrincipalAdmi(context, sc);
				break;

		}

	} while(!opcion.equals("7"));

	}



	//SECCION USUARIOS

	public static void vistaPrincipalUsuario(ConfigurableApplicationContext context, Scanner scanner){
		UsuarioService usuarioController = context.getBean(UsuarioService.class);
		
		System.out.println("¡Bienvenido al menú!");
		String opcion;
		do {
			System.out.println("Seleccione una opción:");
			System.out.println("1. Registrar usuario");
			System.out.println("2. Iniciar sesión");
			System.out.println("3. Salir");
			System.out.print("Opción: ");
			opcion=scanner.nextLine();
			String respuesta;
			switch (opcion) {
				case "1":
					respuesta=usuarioController.registrarUsuario(scanner);
					vistaPrincipalUsuario(context, scanner);
					break;
				case "2":
					respuesta=usuarioController.iniciarSesion(scanner);
					System.out.println(respuesta);
					if(respuesta.equals("-1")){
						break;}
						else{
							mostrarMenuUsuario(context, scanner, respuesta);
						}
					break;
				case "3":
					System.out.print("Para salir ingrese nuevamente su nombre de usuario:");
					String nombre=scanner.nextLine();
					usuarioController.finalizarActividadUsuario(nombre);
					System.out.println("¡Hasta luego!");
					break;
				default:
					System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
			}
		} while (!opcion.equals("3"));
	
	}

	private static void mostrarMenuUsuario(ConfigurableApplicationContext context, Scanner scanner, String usuario) {
		ProductoService productoService= context.getBean(ProductoService.class);
		CarritoService carritoService= context.getBean(CarritoService.class);

		System.out.println("\nBienvenido al menú:");

		String opcionSecundaria;
		do {
			System.out.println("***********************************");
			System.out.println("1. Ver catálogo de productos.");
			System.out.println("2. Ver mi carrito.");
			System.out.println("3. Agregar un producto al carrito.");
			System.out.println("4. Eliminar un producto del carrito.");
			System.out.println("5. Modificar cantidad de un producto.");
			System.out.println("6. Intercambiar un producto por otro.");
			System.out.println("7. Generar pedido."); //Esta funcion convierte un carrito en un pedido y genera la factura. El carrito se vacía.
			System.out.println("8. Pagar facturas."); //De una lista de facturas que tiene el usuario elige cuales pagar.
			System.out.println("9. Ver mis datos personales.");
			System.out.println("10. Cerrar Sesión.");
			System.out.println("***********************************");
			System.out.print("Ingrese la opción: ");
			
			opcionSecundaria=scanner.nextLine();
			switch (opcionSecundaria) {
				case "1":
					productoService.mostrarCatalogo();
					break;
				case "2":
					carritoService.verMiCarrito(usuario);
					break;
				case "3":
					carritoService.agregarProducto(scanner, usuario);
					break;
				case "4":
					carritoService.eliminarProducto(scanner, usuario);
					break;
				case "5":
					carritoService.cambiarCantidad(usuario, scanner);
					break;
				case "6":
					carritoService.intercambiarProducto(usuario, scanner);
					break;
				case "7":
					UsuarioService usuarioService = context.getBean(UsuarioService.class);
					usuarioService.mostrarUsuarioYActividadesPorNombre(usuario);
					break;
				case "8":
					break;
				default:
					System.out.println("Opción no válida. Intente nuevamente.");
			}
		} while (opcionSecundaria != "6");
	}



	//APLICACION MAIN
	public static void main(String[] args) {
		ConfigurableApplicationContext context= SpringApplication.run(SupermercadoApplication.class, args);
		Scanner scanner = new Scanner(System.in);

		principal(context, scanner);

	}
	

}
