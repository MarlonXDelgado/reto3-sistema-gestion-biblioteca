package com.dev.mxd.service;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;
/**
 * Pruebas unitarias para BookService.
 * La idea es validar los flujos felices y los casos de error:
 * - Agregar libros
 * - Buscar por ISBN
 * - Listar
 * - Eliminar
 * - Validar parámetros nulos/vacíos
 */

class BookServiceTest {

    private BookService service;

    @BeforeEach
    void setUp() {
        // Antes de cada test iniciamos un servicio "limpio" en memoria
        service = new BookService();
    }

     /**
     * Verifica que se pueda agregar un libro y recuperarlo correctamente.
     */
    @Test
    void testAddBook() {
        // Given
        // datos de un libro válido
        var isbn = "1234567890";
        var title = "Test Book";
        var author = "Test Author";

        // When
        // agregamos el libro
        service.addBook(isbn, title, author);

        // Then
         // Assert: recuperamos y confirmamos que quedó bien guardado
        var book = service.getBookByIsbn(isbn);
        assertNotNull(book);
        assertEquals(title, book.getTitle()); 
        assertEquals(author, book.getAuthor());
    }

    
    /**
     * Verifica que se pueda eliminar un libro existente
     * y que no sea posible recuperarlo después.
     */
    @Test
    void testDeleteExistedBook() throws NotFoundException {
        // Given
        // primero agregamos un libro
        var isbn = "1234567890";
        var title = "Test Book";
        var author = "Test Author";
        service.addBook(isbn, title, author);

        // When
        // luego lo borramos
        service.deleteBook(isbn);

        // Then
        //Esperamos que al buscarlo lance excepción
        try {
            service.getBookByIsbn(isbn);
            fail();
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }

      /**
     * Verifica que al intentar eliminar un libro que no existe
     * se lance la excepción correspondiente.
     */
    @Test
    void testDeleteNonExistedBook() {
        // Given
        var isbn = "1234567890";

        // When & Then
        assertThrows(NotFoundException.class, () -> service.deleteBook(isbn));
    }

    /**
     * Verifica que si el libro existe pero el ISBN dado no coincide,
     * se lance la excepción NotFoundException.
     */
    @Test
    void deleteWithExistingBookButNotGivenIsbn() {
        // Given
        service.addBook("987654321", "En busca de la felicidad", "marlon delgado");
        var isbn = "1234567890";

        // When & Then
        assertThrows(NotFoundException.class, () -> service.deleteBook(isbn));
    }

    /**
     * Verifica que al agregar un libro, este se pueda listar correctamente.
     */
    @Test
    void testGetAllBooks() {
        // Given
        var isbn = "1234567890";
        var title = "Test Book";
        var author = "Test Author";
        service.addBook(isbn, title, author);

        // When
        var books = service.getAllBooks();

        // Then
        assertEquals(1, books.size());
        assertEquals(isbn, books.get(0).getIsbn());
        assertEquals(title, books.get(0).getTitle());
        assertEquals(author, books.get(0).getAuthor());
    }

      /**
     * Verifica que se pueda obtener un libro existente por su ISBN.
     */
    @Test
    void testGetBookByIsbn() throws NotFoundException {
        // Given
        var isbn = "1234567890";
        var title = "Test Book";
        var author = "Test Author";
        service.addBook(isbn, title, author);

        // When
        var book = service.getBookByIsbn(isbn);

        // Then
        assertNotNull(book);
        assertEquals(isbn, book.getIsbn());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

     /**
     * Verifica que al buscar un ISBN incorrecto se lance NotFoundException.
     */
    @Test
    void testGetBookByIsbnWithWrongIsbn() {
        // Given
        service.addBook("1234567890", "java", "pablo sexto");
        var isbn = "1234567891";

        // When & Then
        assertThrows(NotFoundException.class, () -> service.getBookByIsbn(isbn));
    }

      /**
     * Verifica que la excepción tenga un mensaje claro cuando
     * se busca un libro que no existe.
     */
    @Test
    void testGetBookByIsbnNotFound() {
        // Given
        var nonExistentIsbn = "9999999999";
        
        // When & Then
        var exception = assertThrows(NotFoundException.class, () -> {
            service.getBookByIsbn(nonExistentIsbn);
        });
        
        // Verify the exception message
        assertTrue(exception.getMessage().contains("no fue encontrado"));
    }

    /**
     * Verifica que no se pueda agregar un libro con parámetros nulos.
     */
    @Test
    void testAddBookWithNullParameters() {
        // Given
        String isbn = null;
        String title = null;
        String author = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addBook(isbn, title, author);
        });
    }

    /**
     * Verifica que no se pueda agregar un libro con parámetros vacíos.
     */
    @Test
    void testAddBookWithEmptyParameters() {
        // Given
        String isbn = "";
        String title = "";
        String author = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addBook(isbn, title, author);
        });
    }

    /**
     * Verifica que no se pueda buscar un libro con ISBN nulo.
     */
    @Test
    void testGetBookByIsbnWithNullIsbn() {
        // Given
        String isbn = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.getBookByIsbn(isbn);
        });
    }

     /**
     * Verifica que no se pueda eliminar un libro con ISBN nulo.
     */
    @Test
    void testDeleteBookWithNullIsbn() {
        // Given
        String isbn = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteBook(isbn);
        });
    }

     /**
     * Verifica que la lista de libros devuelta no sea nula
     * y esté vacía cuando no se ha agregado ninguno.
     */
    @Test
    void testGetAllBooksWhenEmpty() {
        // Given - service starts empty
        
        // When
        var books = service.getAllBooks();
        
        // Then
        assertNotNull(books);
        assertEquals(0, books.size());
    }

    
    /**
     * Verifica que se puedan agregar varios libros y listarlos correctamente.
     */
    @Test
    void testMultipleBooksOperations() {
        // Given
        var isbn1 = "1234567890";
        var isbn2 = "0987654321";
        var title1 = "Test Book 1";
        var title2 = "Test Book 2";
        var author1 = "Test Author 1";
        var author2 = "Test Author 2";

        // When
        service.addBook(isbn1, title1, author1);
        service.addBook(isbn2, title2, author2);

        // Then
        var books = service.getAllBooks();
        assertEquals(2, books.size());
        
        var book1 = service.getBookByIsbn(isbn1);
        var book2 = service.getBookByIsbn(isbn2);
        
        assertNotNull(book1);
        assertNotNull(book2);
        assertEquals(title1, book1.getTitle());
        assertEquals(title2, book2.getTitle());
    }
    //// --- Casos adicionales para cubrir cada condición de validación ---
    @Test
    void testAddBookWithNullIsbn() {
        assertThrows(IllegalArgumentException.class, () -> service.addBook(null, "Titulo", "Autor"));
    }

    @Test
    void testAddBookWithNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> service.addBook("123", "Titulo", null));
    }

    @Test
    void testAddBookWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> service.addBook("123", null, "Autor"));
    }

    @Test
    void testAddBookWithBlankIsbn() {
        assertThrows(IllegalArgumentException.class, () -> service.addBook("   ", "Titulo", "Autor"));
    }

    @Test
    void testAddBookWithBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> service.addBook("123", "   ", "Autor"));
    }
    
    @Test
    void testAddBookWithBlankAuthor() {
        assertThrows(IllegalArgumentException.class, () -> service.addBook("123", "Titulo", "   "));
    }

}

