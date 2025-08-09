package com.dev.mxd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoanTest {
    @Test
    void setStateToFinished() {
        var b = new Book("111", "Libro", "Autor");
        var u = new User("u1", "Ana", "ana@mail.com");
        var loan = new Loan(u, b);

        loan.setState(LoanState.FINISHED);

        assertEquals(LoanState.FINISHED, loan.getState());
    }

    @Test
    @DisplayName("Constructor (User, Book, LocalDate) inicializa y deja STARTED por defecto")
    void constructorConFechaAsignaStarted() {
        var user = new User("U1", "John Doe", "john@doe.com");   // ← 3 parámetros
        var book = new Book("111", "Clean Code", "Robert C. Martin");
        var loanDate = LocalDate.of(2025, 8, 9);

        var loan = new Loan(user, book, loanDate);

        assertSame(user, loan.getUser());
        assertSame(book, loan.getBook());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(LoanState.STARTED, loan.getState());
    } 

}
