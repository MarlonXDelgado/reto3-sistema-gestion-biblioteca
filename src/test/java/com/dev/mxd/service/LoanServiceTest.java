package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.Book;
import com.dev.mxd.model.LoanState;
import com.dev.mxd.model.User;


/**
 * Clase de pruebas unitarias para {@link LoanService}.
 * 
 * Aquí validamos la lógica principal de los préstamos:
 * - Creación de préstamos.
 * - Devolución de libros.
 * - Manejo de casos con usuarios/libros inexistentes.
 * - Reglas para evitar prestar libros ya prestados.
 * - Validación de parámetros nulos.
 * 
 * Se utilizan mocks de {@link BookService} y {@link UserService} para aislar
 * las pruebas de la lógica interna de esos servicios.
 */
public class LoanServiceTest {

    private LoanService service;
    private BookService bookService;
    private UserService userService;

    /**
     * Antes de cada prueba se crean mocks de BookService y UserService,
     * y se inyectan en una nueva instancia de LoanService.
     */
    @BeforeEach
    void setUp() {
        bookService = Mockito.mock(BookService.class);
        userService = Mockito.mock(UserService.class);
        service = new LoanService(bookService, userService);
    }

     /**
     * Verifica que se pueda crear un préstamo cuando el usuario y el libro existen.
     * Debe registrarse un préstamo en estado STARTED.
     */
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

    /**
     * Verifica que devolver un libro existente actualice el estado del préstamo a FINISHED.
     */
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

      /**
     * Verifica que intentar devolver un libro que nunca fue prestado lance NotFoundException.
     */
    @Test
    void testReturnBookWithNonExistingLoan() {
        // Given
        var id = "123";
        var isbn = "1234567890";

        // When y Then
        assertThrows(NotFoundException.class, () -> service.returnBook(id, isbn)); 
        
    } 

      /**
     * Verifica que no se pueda crear un préstamo si el usuario no existe.
     */
    @Test
    void testAddLoanWithNonExistentUser() {
        // Given
        var userId = "999";
        var isbn = "1234567890";
        var book = new Book(isbn, "Java Avanzado", "Luis Pérez");

        Mockito.when(userService.getUserById(userId)).thenThrow(new NotFoundException("Usuario no encontrado"));
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(book);

        // When & Then
        assertThrows(NotFoundException.class, () -> service.addLoan(userId, isbn));
    }

     /**
     * Verifica que no se pueda crear un préstamo si el libro no existe.
     */
    @Test
    void testAddLoanWithNonExistentBook() {
        // Given
        var userId = "123";
        var isbn = "9999999999";
        var user = new User(userId, "Ana", "ana@mail.com");

        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenThrow(new NotFoundException("Libro no encontrado"));

        // When & Then
        assertThrows(NotFoundException.class, () -> service.addLoan(userId, isbn));
    }

      /**
     * Verifica que no se pueda prestar un libro que ya está prestado.
     * El mensaje de error debe indicar que está prestado.
     */
    @Test
    @DisplayName("No permite prestar un libro ya prestado (lanza NotFoundException)")
    void testAddLoanWithAlreadyLoanedBookThrowsNotFoundException() {
        // Given
        var id = "123";
        var isbn = "1234567890";
        var mockUser = new User(id, "Xavi", "xavi@gmail.com");
        var mockBook = new Book(isbn, "Java Master", "Kevin Sanchez");

        Mockito.when(userService.getUserById(id)).thenReturn(mockUser);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);

        // Préstamo inicial activo
        service.addLoan(id, isbn);

        // When & Then
        var ex = assertThrows(NotFoundException.class, () -> service.addLoan(id, isbn));

        // Valida mensaje de forma robusta (ignora acentos y mayúsculas)
        String msg = String.valueOf(ex.getMessage()).toLowerCase();
        assertTrue(
           msg.contains("prestado") || msg.contains("préstamo"),
          "Mensaje inesperado: " + ex.getMessage()
        );

        // Verificaciones no estrictas
        Mockito.verify(userService, Mockito.atLeastOnce()).getUserById(id);
        Mockito.verify(bookService, Mockito.atLeastOnce()).getBookByIsbn(isbn);
    }

     /**
     * Valida que addLoan lance excepción si los parámetros son nulos.
     */
    @Test
    void testAddLoanWithNullParams() {
        assertThrows(IllegalArgumentException.class, () -> service.addLoan(null, "111"));
        assertThrows(IllegalArgumentException.class, () -> service.addLoan("u1", null));
    }

     /**
     * Valida que returnBook lance excepción si los parámetros son nulos.
     */
    @Test
    void testReturnBookWithNullParams() {
        assertThrows(IllegalArgumentException.class, () -> service.returnBook(null, "111"));
        assertThrows(IllegalArgumentException.class, () -> service.returnBook("u1", null));
    }

    /**
     * Verifica que el mensaje al intentar prestar un libro ya prestado contenga ISBN y la palabra "prestado".
     */
    @Test
    void testAddLoanWithAlreadyLoanedBookThrowsNotFoundException_FuzzyMessage() {
        var id = "123";
        var isbn = "1234567890";
        var user = new User(id, "Xavi", "xavi@gmail.com");
        var book = new Book(isbn, "Java Master", "Kevin Sanchez");

        Mockito.when(userService.getUserById(id)).thenReturn(user);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(book);

        service.addLoan(id, isbn); // préstamo activo

        var ex = assertThrows(NotFoundException.class, () -> service.addLoan(id, isbn));
        var msg = String.valueOf(ex.getMessage()).toLowerCase();
        assertTrue(msg.contains("isbn") && msg.contains("prestado")); 
        assertTrue(msg.contains(isbn));
    }

     /**
     * Verifica que devolver un libro con ISBN distinto al prestado lance NotFoundException.
     */
    @Test
    void testReturnBookWithDifferentIsbnThrowsNotFound() {
        var id = "u1";
        var isbnOk = "111";
        var isbnWrong = "222";

        Mockito.when(userService.getUserById(id)).thenReturn(new User(id, "Ana", "a@a.com"));
        Mockito.when(bookService.getBookByIsbn(isbnOk)).thenReturn(new Book(isbnOk, "L1", "A1"));
        Mockito.when(bookService.getBookByIsbn(isbnWrong)).thenReturn(new Book(isbnWrong, "L2", "A2"));

        service.addLoan(id, isbnOk);

        var ex = assertThrows(NotFoundException.class, () -> service.returnBook(id, isbnWrong));
        var msg = String.valueOf(ex.getMessage()).toLowerCase();
        assertTrue(msg.contains("no hay un prestamo del libro"));
        assertTrue(msg.contains(isbnWrong));
        assertTrue(msg.contains(id));
    }

     /**
     * Verifica que devolver un libro con un usuario distinto al que lo prestó lance NotFoundException.
     */
    @Test
    void testReturnBookWithDifferentUserThrowsNotFound() {
        var idOk = "u1";
        var idWrong = "u2";
        var isbn = "111";

        Mockito.when(userService.getUserById(idOk)).thenReturn(new User(idOk, "Ana", "a@a.com"));
        Mockito.when(userService.getUserById(idWrong)).thenReturn(new User(idWrong, "Luis", "l@l.com"));
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(new Book(isbn, "L", "A"));

        service.addLoan(idOk, isbn);

        var ex = assertThrows(NotFoundException.class, () -> service.returnBook(idWrong, isbn));
        var msg = String.valueOf(ex.getMessage()).toLowerCase();
        assertTrue(msg.contains("no hay un prestamo del libro"));
        assertTrue(msg.contains(isbn));
        assertTrue(msg.contains(idWrong));
    }

    /**
     * Verifica que intentar devolver dos veces el mismo libro falle en el segundo intento.
     */
    @Test
    void testReturnBookTwiceThrowsNotFound() throws NotFoundException {
        var id = "u1"; var isbn = "111";
        Mockito.when(userService.getUserById(id)).thenReturn(new User(id, "Ana", "a@a.com"));
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(new Book(isbn, "L", "A"));

        service.addLoan(id, isbn);
        service.returnBook(id, isbn); // primera OK

        assertThrows(NotFoundException.class, () -> service.returnBook(id, isbn)); // segunda falla
    }

     /**
     * Verifica que getLoanDate devuelva una fecha válida después de agregar un préstamo.
     */
    @Test
    void testGetLoanDateAfterAddLoan() {
        var id = "u1"; var isbn = "111";
        Mockito.when(userService.getUserById(id)).thenReturn(new User(id, "Ana", "a@a.com"));
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(new Book(isbn, "L", "A"));

        service.addLoan(id, isbn);

        var date = service.getLoanDate();
        assertNotNull(date); 
    }

    /**
     * Verifica que getLoanDate lance IndexOutOfBoundsException si no hay préstamos.
     */
    @Test
    void testGetLoanDateWhenNoLoansThrowsIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> service.getLoanDate());
    }

    /**
     * Verifica que se puedan prestar libros diferentes aunque ya exista otro préstamo activo.
     */
    @Test
    @DisplayName("addLoan no bloquea cuando hay préstamos activos de OTRO ISBN (rama false del if)")
    void testAddLoanSkipsWhenOtherIsbnActive_LoopsFalseBranch() {
        // Given
        var userId = "u1";
        var isbn1 = "111"; // préstamo ya activo
        var isbn2 = "222"; // nuevo préstamo que queremos crear

        var user = new User(userId, "Ana", "ana@mail.com");
        var book1 = new Book(isbn1, "Libro 1", "Autor 1");
        var book2 = new Book(isbn2, "Libro 2", "Autor 2");

        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        Mockito.when(bookService.getBookByIsbn(isbn1)).thenReturn(book1);
        Mockito.when(bookService.getBookByIsbn(isbn2)).thenReturn(book2);

        // Hay un préstamo STARTED pero de OTRO ISBN
        service.addLoan(userId, isbn1);

        // When (debe iterar sobre la lista, evaluar el if como FALSE y permitir el préstamo)
        service.addLoan(userId, isbn2);

        // Then
        var loans = service.getLoans();
        assertEquals(2, loans.size());
        assertEquals(isbn1, loans.get(0).getBook().getIsbn());
        assertEquals(isbn2, loans.get(1).getBook().getIsbn());
        // Ambos deben quedar STARTED
        assertEquals(LoanState.STARTED, loans.get(0).getState());
        assertEquals(LoanState.STARTED, loans.get(1).getState());
    }

     /**
     * Verifica que se pueda volver a prestar el mismo libro si el préstamo anterior fue finalizado.
     */
    @Test
    @DisplayName("addLoan permite volver a prestar MISMO ISBN si el préstamo previo ya está FINISHED (rama false del if)")
    void testAddLoanAllowsSameIsbnAfterFinished_LoopsFalseBranch() throws NotFoundException {
        // Given
        var userId = "u1";
        var isbn = "111";

        var user = new User(userId, "Ana", "ana@mail.com");
        var book = new Book(isbn, "Libro 1", "Autor 1");

        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(book);

        // 1) Prestar
        service.addLoan(userId, isbn);
        // 2) Devolver (queda FINISHED)
        service.returnBook(userId, isbn);
        // When: volver a prestar MISMO ISBN
        service.addLoan(userId, isbn);

        // Then: deben existir 2 préstamos (uno FINISHED y el nuevo STARTED)
        var loans = service.getLoans();
        assertEquals(2, loans.size());
        assertEquals(LoanState.FINISHED, loans.get(0).getState());
        assertEquals(LoanState.STARTED, loans.get(1).getState());
        assertEquals(isbn, loans.get(0).getBook().getIsbn());
        assertEquals(isbn, loans.get(1).getBook().getIsbn());
    }

}
