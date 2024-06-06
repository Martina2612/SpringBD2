package com.example.demo;

/*El Memento es un patron de diseño que permite capturar y almacenar el estado interno de un objeto 
para poder restaurarlo más tarde sin violar el principio de encapsulación*/

public class CarritoMemento {
    private Carrito carrito;

    public CarritoMemento(Carrito carrito) {
        this.carrito = carrito;
    }

    public Carrito getEstadoGuardado() {
        return carrito;
    }
}
