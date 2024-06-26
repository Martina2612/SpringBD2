package com.example.supermercado.services;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.neo4j.driver.Record;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermercado.modules.ActividadAdministrador;
import com.example.supermercado.modules.Administrador;
import com.example.supermercado.modules.Producto;
import com.example.supermercado.repositories.ActividadAdministradorRepository;
import com.example.supermercado.repositories.AdministradorRepository;
import com.example.supermercado.repositories.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    AdministradorRepository adminRepository;

    @Autowired
    ActividadAdministradorRepository actAdministrador;


    //METODOS PARA LOS USUARIOS Y ADMINISTRADORES

    public void mostrarCatalogo(){
        System.out.println("---------------Bienvenido al catálogo-------------");
        List<Producto> productos = productoRepository.findAll();

        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles en el catálogo.");
        } else {
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getId());
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("Descripcion: " + producto.getDescripcion());
                System.out.println("Comentario: " + producto.getComentario());
                System.out.println("Precio: " + producto.getPrecio());
                System.out.println("Stock: " + producto.getStock());
                System.out.println("---------------------------------------------------------------");
            }
        }
    }

    //METODOS PARA EL ADMINISTRADOR

    public void mostrarRegistroModificaciones(){
        System.out.println("---------------Registro de Modificaciones en el Catálogo-------------");
        List<ActividadAdministrador> actividades= actAdministrador.findAll();
        for (ActividadAdministrador actividad : actividades){
            System.out.println("Producto modificado: "+ actividad.getProducto().getNombre()+ "    ID: "+ actividad.getProducto().getId());
            System.out.println("Anterior valor: "+ actividad.getValorAnterior());
            System.out.println("Nuevo valor: "+ actividad.getValorActual());
            System.out.println("Operador Interviniente Nro: "+ actividad.getNroOperador());
            System.out.println("---------------------------------------------------------------");
        }
        
    }

    public void registrarModificaciones(String administrador, Producto producto, String viejoValor, String nuevoValor){
        ActividadAdministrador actividad = new ActividadAdministrador();
        Optional<Administrador> admin=adminRepository.findByEmail(administrador);

        String numOperador= admin.get().getNroOperador();

        actividad.setNroOperador(numOperador);
        actividad.setProducto(producto);
        actividad.setValorAnterior(viejoValor);
        actividad.setValorActual(nuevoValor);
        
        actAdministrador.save(actividad);
    }

    public void modificaciones(Scanner sc, String administrador){
        System.out.print("Ingrese el producto que desea modificar: ");
        String modificar = sc.nextLine();
        Producto producto= existeProducto(modificar);
        if(producto!=null){
            String nuevoValor="0";
            String viejoValor="0";
            String opcion;
            do{
            System.out.println("Qué desea modificar del producto? ");
            System.out.println("1. Nombre");
            System.out.println("2. Descripcion");
            System.out.println("3. Comentario");
            System.out.println("4. Precio");
            System.out.println("5. Stock");
            System.out.print("Opción: ");
            opcion=sc.nextLine();
            switch (opcion){
                case "1":
                    viejoValor=producto.getNombre();
                    System.out.print("Nuevo: ");
                    nuevoValor=sc.nextLine();
                    producto.setNombre(nuevoValor);
                    break;
                case "2":
                    viejoValor=producto.getDescripcion();
                    System.out.print("Nuevo: ");
                    nuevoValor=sc.nextLine();
                    producto.setDescripcion(nuevoValor);
                    break;
                case "3":
                    viejoValor=producto.getComentario();
                    System.out.print("Nuevo: ");
                    nuevoValor=sc.nextLine();
                    producto.setComentario(nuevoValor);
                    break;
                case "4":
                    viejoValor = String.valueOf(producto.getPrecio());
                    System.out.print("Nuevo: ");
                    nuevoValor=sc.nextLine();
                    double precio$ = Double.parseDouble(nuevoValor);
                    producto.setPrecio(precio$);
                    break;
                case "5":
                    viejoValor = String.valueOf(producto.getStock());
                    System.out.print("Nuevo: ");
                    nuevoValor=sc.nextLine();
                    int stockInt = Integer.parseInt(nuevoValor);
                    producto.setStock(stockInt);
                    break;
            }
            productoRepository.save(producto);
            registrarModificaciones(administrador,producto,viejoValor,nuevoValor);
        } while(!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3") && !opcion.equals("4") && !opcion.equals("5"));
        }

    }

    public void crearProducto(Scanner sc){
        
        System.out.print("Ingreso de datos del producto ");
        System.out.print("Nombre: ");
        String nombreProducto = sc.nextLine();

        if(existeProducto(nombreProducto)==null){
            System.out.print("Descripcion: ");
            String descripcion = sc.nextLine();

            System.out.print("Comentario: ");
            String comentario = sc.nextLine();

            System.out.print("Stock: ");
            String stock = sc.nextLine();
            //convertir el stock a int
            int stockInt = Integer.parseInt(stock);

            System.out.print("Precio: ");
            String precio = sc.nextLine();
            //convertir el precio a double
            double precio$ = Double.parseDouble(precio);

            Producto producto = new Producto();
            producto.setNombre(nombreProducto);
            producto.setDescripcion(descripcion);
            producto.setComentario(comentario);
            producto.setStock(stockInt);
            producto.setPrecio(precio$);

            productoRepository.save(producto);

            System.out.println("Se ha agregado el producto al catálogo.");
        }else{
            System.out.println("Ya existe un producto con ese nombre en el carrito.");
        }
    }
    

    private Producto existeProducto(String nombre){
        Producto p = productoRepository.findByNombre(nombre);
        return p;
    }


    public void eliminarProducto(Scanner sc){
        System.out.print("Ingrese el nombre del producto que desea eliminar: ");
        String nombre= sc.nextLine();
        try(Driver driver= GraphDatabase.driver("bolt://localhost:7687")){
            try(Session session = driver.session()){
                String query=("MATCH (p:Producto {nombre: $nombre})\n" + //
                                        "DETACH DELETE p");
                session.run(query,Values.parameters("nombre",nombre));
            }
        }
    }


    public void mostrarListaPrecios(){
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687")) {
            try (Session session = driver.session()) {
                Result result = session.run(
                    "MATCH (p:Producto) " +
                    "RETURN p.nombre AS nombre, p.precio AS precio");
                
                System.out.println("====================================================");
                System.out.println("              LISTA DE PRECIOS ACTUALIZADA          ");
                while (result.hasNext()) {
                    Record record = result.next();
    
                    String nombre = record.get("nombre").asString();
                    double precio = record.get("precio").asDouble();
                    
                    System.out.println("Producto: " + nombre+"       Precio: "+precio);
                    System.out.println("_____________________________________________________");
                }
                System.out.println("====================================================");
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de precios: " + e.getMessage());
        }
    }



}
