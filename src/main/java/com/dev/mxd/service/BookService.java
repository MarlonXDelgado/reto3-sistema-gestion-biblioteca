package com.dev.mxd.service;

import java.util.ArrayList;
import java.util.List;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.Book;

public class BookService {

    private List<Book> books;

    public BookService() {
        books = new ArrayList<>();
    }

    public void addBook(String isbn, String title, String author) {
        books.add(new Book(isbn, title, author)); 
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookByIsbn(String isbn) throws NotFoundException{
        for (var book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        throw new NotFoundException("El libro con el isbn " + isbn + " no fue encontrado"); 
    }

    public void deleteBook(String isbn) throws NotFoundException{
        for (var book : books) {
            if (book.getIsbn().equals(isbn)) {
                books.remove(book);
                return; // Retornar después de eliminar el libro
            }
        }
        throw new NotFoundException("El libro con el isbn " + isbn + " no pudo ser borrado");
    }
}
