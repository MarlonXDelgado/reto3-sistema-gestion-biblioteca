package com.dev.mxd.model; // Aquí indicamos en qué paquete está la clase, para mantener todo organizado.

import java.time.LocalDate; // Aquí indicamos en qué paquete está la clase, para mantener todo organizado.
/**
 * Representa un préstamo de un libro a un usuario.
 * Guarda quién lo pidió, qué libro es, cuándo se lo llevó y en qué estado está el préstamo.
 */
public class Loan {
// Atributos privados para que nadie pueda cambiarlos desde fuera sin control
    private User user;// Usuario que pidió el préstamo
    private Book book;// Libro que fue prestado
    private LocalDate loanDate;// Fecha en la que se realizó el préstamo
    private LoanState state;//// Estado actual del préstamo (ej: STARTED, FINISHED)
     /**
     * Constructor que recibe el usuario y el libro.
     * La fecha se pone automáticamente a "hoy" y el estado queda en STARTED.
     */
    public Loan(User user, Book book) {
        this(user, book, LocalDate.now(), LoanState.STARTED);
    }
    /**
     * Constructor que recibe usuario, libro y fecha.
     * El estado siempre se pondrá en STARTED.
     */
    public Loan(User user, Book book, LocalDate loanDate) {
        this(user, book, loanDate, LoanState.STARTED);
    }
     /**
     * Constructor más completo.
     * Permite indicar usuario, libro, fecha y estado directamente.
     */
    public Loan(User user, Book book, LocalDate loanDate, LoanState state) {
        this.user = user;// Guardamos el usuario
        this.book = book;// Guardamos el libro
        this.loanDate = loanDate;// Guardamos la fecha
        this.state = state;// Guardamos el estado
    }

    // Métodos "get" para consultar los datos (no cambian nada)

    public User getUser() {
        return user;// Devuelve el usuario que pidió el préstamo
    }

    public Book getBook() {
        return book;// Devuelve el libro prestado
    }

    public LocalDate getLoanDate() {
        return loanDate; // Devuelve la fecha del préstamo
    }

    public LoanState getState() {
        return state; // Devuelve el estado del préstamo
    }

     // Método "set" para cambiar el estado del préstamo

    public void setState(LoanState state) {
        this.state = state; // Método "set" para cambiar el estado del préstamo
    }

    
}
