package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    void testReturnBook() {

    }
}
