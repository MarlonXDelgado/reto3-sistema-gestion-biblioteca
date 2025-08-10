package com.dev.mxd.exception;

// Excepción personalizada que se lanza cuando no se encuentra un recurso.
// Por ejemplo: un libro, usuario o préstamo inexistente en el sistema.
public class NotFoundException extends RuntimeException{

// Constructor que recibe un mensaje y lo envía a la clase padre 
    public NotFoundException(String message) {
        super(message);
    }
}
