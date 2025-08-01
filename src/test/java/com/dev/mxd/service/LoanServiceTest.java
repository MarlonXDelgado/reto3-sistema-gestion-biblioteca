package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.Book;
import com.dev.mxd.model.LoanState;
import com.dev.mxd.model.User;

public class LoanServiceTest {

    private LoanService service;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        bookService = Mockito.mock(BookService.class);
        userService = Mockito.mock(UserService.class);
        service = new LoanService(bookService, userService);
    }

    @DisplayName("Agregar un prestamo con usuario y libro existentes")
    @Test
    void testAddLoanWithExistingUserAndExistingBook() {
        // Given
        var id = "123";
        var isbn = "1234567890";
        var mockUser = new User(id, "Xavi", "xavi@gmail.com");
        var mockBook = new Book(isbn, "Java Master", "Kevin Sanchez");

        Mockito.when(userService.getUserById(id)).thenReturn(mockUser);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);

        // When
        service.addLoan(id, isbn);

        // Then
        var loans = service.getLoans();
        assertEquals(1, loans.size());

        var loan = loans.get(0);
        assertNotNull(loan.getUser());
        assertNotNull(loan.getBook());
        assertEquals(LoanState.STARTED, loan.getState());

    }

    @Test
    void testReturnBookWithExistingLoan() throws NotFoundException {
        // Given
        var id = "123";
        var isbn = "1234567890";
        var mockUser = new User(id, "Xavi", "xavi@gmail.com"); 
        var mockBook = new Book(isbn, "Java Master", "Kevin Sanchez");

        Mockito.when(userService.getUserById(id)).thenReturn(mockUser);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);

        service.addLoan(id, isbn);

        // When
        service.returnBook(id, isbn);

        // Then
        var loans = service.getLoans();
        assertEquals(1, loans.size());

        var loan = loans.getFirst();
        assertEquals(id, loan.getUser().getId());
        assertEquals(isbn, loan.getBook().getIsbn());
        assertEquals(LoanState.FINISHED, loan.getState());

    }

    @Test
    void testReturnBookWithNonExistingLoan() {
        // Given
        var id = "123";
        var isbn = "1234567890";

        // When y Then
        assertThrows(NotFoundException.class, () -> service.returnBook(id, isbn)); 
        
    }

    

    
}
