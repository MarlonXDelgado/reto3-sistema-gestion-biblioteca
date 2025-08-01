package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;

class BookServiceTest {

    private BookService service;

    @BeforeEach
    void setUp() {
        service = new BookService();
    }

    @Test
    void testAddBook() {
        // Given
        var isbn = "1234567890";
        var title = "Test Book";
        var author = "Test Author";

        // When
        service.addBook(isbn, title, author);

        // Then
        var book = service.getBookByIsbn(isbn);
        assertNotNull(book);
        assertEquals(title, book.getTitle()); 
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testDeleteExistedBook() throws NotFoundException {
        // Given
        var isbn = "1234567890";
        var title = "Test Book";
        var author = "Test Author";
        service.addBook(isbn, title, author);

        // When
        service.deleteBook(isbn);

        // Then
        try {
            service.getBookByIsbn(isbn);
            fail();
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void testDeleteNonExistedBook() {
        // Given
        var isbn = "1234567890";

        // When & Then
        assertThrows(NotFoundException.class, () -> service.deleteBook(isbn));
    }

    @Test
    void deleteWithExistingBookButNotGivenIsbn() {
        // Given
        service.addBook("987654321", "En busca de la felicidad", "marlon delgado");
        var isbn = "1234567890";

        // When & Then
        assertThrows(NotFoundException.class, () -> service.deleteBook(isbn));
    }

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

    @Test
    void testGetBookByIsbnWithWrongIsbn() {
        // Given
        service.addBook("1234567890", "java", "pablo sexto");
        var isbn = "1234567891";

        // When & Then
        assertThrows(NotFoundException.class, () -> service.getBookByIsbn(isbn));
    }

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

    @Test
    void testGetBookByIsbnWithNullIsbn() {
        // Given
        String isbn = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.getBookByIsbn(isbn);
        });
    }

    @Test
    void testDeleteBookWithNullIsbn() {
        // Given
        String isbn = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteBook(isbn);
        });
    }

    @Test
    void testGetAllBooksWhenEmpty() {
        // Given - service starts empty
        
        // When
        var books = service.getAllBooks();
        
        // Then
        assertNotNull(books);
        assertEquals(0, books.size());
    }

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
} 