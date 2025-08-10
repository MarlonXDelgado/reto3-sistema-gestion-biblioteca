package com.dev.mxd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para el modelo Book.
 * Se encarga de verificar que el constructor y los getters
 * funcionen correctamente, asegurando que los datos del libro
 * se almacenen y se recuperen sin errores.
 */

public class BookTest {

    /**
     * Verifica que el constructor asigne correctamente los valores
     * y que los métodos getters devuelvan la información esperada.
     */
     @Test
    void constructorAndGetters() {
        // Given: datos de un libro
        var b = new Book("111", "Clean Code", "Robert C. Martin");

        // Then: se espera que los getters devuelvan los mismos valores asignados
        assertEquals("111", b.getIsbn());
        assertEquals("Clean Code", b.getTitle());
        assertEquals("Robert C. Martin", b.getAuthor());
    }
    
}
