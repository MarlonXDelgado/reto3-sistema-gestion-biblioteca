package com.dev.mxd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.Loan;
import com.dev.mxd.model.LoanState;

// Servicio que se encarga de los prestamos de libros:
// crear un préstamo, devolver un libro, listar prestamos y consultar la fecha.
public class LoanService {
    // Base de datos en memoria con todos los préstamos
    private List<Loan> loans;
    // Dependencias para consultar libros y usuarios existentes
    private BookService bookService;
    private UserService userService;

    // Al construir el servicio, inyectamos los otros servicios y arrancamos la lista vacía
    public LoanService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loans = new ArrayList<>();
    }

    // Crea un nuevo préstamo para el usuario (id) y el libro (isbn).
    public void addLoan(String id, String isbn) throws NotFoundException {
        //no acepta parámetros nulos
        if (id == null || isbn == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }

        // Busca el usuario y el libro (si no existen, los servicios lanzan NotFoundException)
        var user = userService.getUserById(id);
        var book = bookService.getBookByIsbn(isbn);

        // Antes de prestar, asegura que el libro no tenga un préstamo activo
        for (var loan : loans) {
            if (loan.getBook().getIsbn().equals(isbn) && loan.getState().equals(LoanState.STARTED)) {
                // Si ya hay un préstamo activo para ese ISBN, no permite otro
                throw new NotFoundException("El libro con el ISBN: "
                                    +isbn + " ya esta prestado");
            }
        }
        
        // Si todo bien, crea el préstamo; Loan pone fecha y estado STARTED
        loans.add(new Loan(user, book)); 
    }

     // Marca como devuelto el préstamo que coincide con usuario + isbn y que esté STARTED.
    public void returnBook(String id, String isbn) throws NotFoundException {
        if (id == null || isbn == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        // Busca un préstamo activo que coincida con el usuario y el ISBN
        for (var loan : loans) {
            if (loan.getUser().getId().equals(id)
                  && loan.getBook().getIsbn().equals(isbn)
                  && loan.getState().equals(LoanState.STARTED)) {
                loan.setState(LoanState.FINISHED);
                return;
            }
        }

        // Si no encuentra un préstamo que coincida, avisa con una excepción clara
        throw new NotFoundException("no hay un prestamo del libro:"
                                    +isbn + " para el usuario: "+id);
    }

    // Devuelve la lista de préstamos
    public List<Loan> getLoans() {
        return loans;
    }

    // Devuelve la fecha del primer préstamo de la lista
    public LocalDate getLoanDate() {
        // Si no hay préstamos, avisa con un error claro
        if (loans.isEmpty()) {
            throw new IndexOutOfBoundsException("No hay préstamos registrados");
        }
        return loans.get(0).getLoanDate();
    }
    


}
