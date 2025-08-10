package com.dev.mxd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/**
 * Clase de pruebas para el modelo Loan.
 * Aquí verificamos que los constructores, getters y setters
 * funcionen como se espera, especialmente en el manejo
 * del estado del préstamo y la inicialización correcta de sus datos.
 */
public class LoanTest {

     /**
     * Verifica que el método setState() cambie el estado de un préstamo a FINISHED.
     */
    @Test
    void setStateToFinished() {
        // Given: un préstamo con libro y usuario
        var b = new Book("111", "Libro", "Autor");
        var u = new User("u1", "Ana", "ana@mail.com");
        var loan = new Loan(u, b);

        // When: se cambia el estado a FINISHED
        loan.setState(LoanState.FINISHED);

        // Then: el estado debe reflejar el cambio
        assertEquals(LoanState.FINISHED, loan.getState());
    }

    /**
     * Verifica que el constructor (User, Book, LocalDate) asigne
     * correctamente usuario, libro y fecha, dejando el estado en STARTED por defecto.
     */
    @Test
    @DisplayName("Constructor (User, Book, LocalDate) inicializa y deja STARTED por defecto")
    void constructorConFechaAsignaStarted() {
        // Given: usuario, libro y fecha de préstamo
        var user = new User("U1", "John Doe", "john@doe.com");   
        var book = new Book("111", "Clean Code", "Robert C. Martin");
        var loanDate = LocalDate.of(2025, 8, 9);

        // When: se crea el préstamo con esos datos
        var loan = new Loan(user, book, loanDate);

        // Then: se espera que todos los campos estén correctamente asignados
        assertSame(user, loan.getUser());
        assertSame(book, loan.getBook());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(LoanState.STARTED, loan.getState());
    } 

}
