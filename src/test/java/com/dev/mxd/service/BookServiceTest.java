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
} 