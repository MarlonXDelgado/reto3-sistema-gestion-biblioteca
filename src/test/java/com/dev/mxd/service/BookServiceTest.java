package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.Book;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void testAddBook() {
        // Given
        String isbn = "1234567890";
        String title = "Test Book";
        String author = "Test Author";

        // When
        bookService.addBook(isbn, title, author);

        // Then
        assertEquals(1, bookService.getAllBooks().size());
        Book book = bookService.getAllBooks().get(0);
        assertEquals(isbn, book.getIsbn());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testGetBookByIsbn() throws NotFoundException {
        // Given
        String isbn = "1234567890";
        bookService.addBook(isbn, "Test Book", "Test Author");

        // When
        Book foundBook = bookService.getBookByIsbn(isbn);

        // Then
        assertNotNull(foundBook);
        assertEquals(isbn, foundBook.getIsbn());
    }

    @Test
    void testGetBookByIsbnNotFound() {
        // Given
        String nonExistentIsbn = "9999999999";

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            bookService.getBookByIsbn(nonExistentIsbn);
        });
    }

    @Test
    void testGetAllBooks() {
        // Given
        bookService.addBook("1", "Book 1", "Author 1");
        bookService.addBook("2", "Book 2", "Author 2");

        // When
        var books = bookService.getAllBooks();

        // Then
        assertEquals(2, books.size());
    }
} 