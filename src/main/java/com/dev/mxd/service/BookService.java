package com.dev.mxd.service;

import java.util.ArrayList;
import java.util.List;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.Book;
// Servicio que maneja toda la lógica relacionada con los libros.
// Aquí podemos agregar, eliminar, buscar y listar todos los libros.
public class BookService {

    // Lista donde se guardan todos los libros del sistema
    private List<Book> books;

    // Constructor: inicializa la lista de libros vacía
    public BookService() {
        books = new ArrayList<>();
    }

    // Agrega un nuevo libro al sistema
    public void addBook(String isbn, String title, String author) {
        // Validaciones para evitar datos inválidos
        if (isbn == null || title == null || author == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (isbn.trim().isEmpty() || title.trim().isEmpty() || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
        }

        // Crea un nuevo libro y lo agrega a la lista
        books.add(new Book(isbn, title, author)); 
    }

    // Devuelve todos los libros registrados.
    public List<Book> getAllBooks() {
        return books;
    }

    // Busca un libro por su ISBN y lo devuelve si existe.
    public Book getBookByIsbn(String isbn) throws NotFoundException{
        // No acepta ISBN nulo
        if (isbn == null) {
            throw new IllegalArgumentException("El ISBN no puede ser nulo");
        }
        // Recorre la lista hasta encontrar coincidencia exacta de ISBN
        for (var book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;// encontrado
            }
        }
        // Si llega aquí, no estaba en la lista
        throw new NotFoundException("El libro con el isbn " + isbn + " no fue encontrado"); 
    }

    // Elimina un libro por su ISBN.
    public void deleteBook(String isbn) throws NotFoundException{
        // Validación de entrada
        if (isbn == null) {
            throw new IllegalArgumentException("El ISBN no puede ser nulo");
        }
        // Busca el libro y, si está, lo quita de la lista
        for (var book : books) {
            if (book.getIsbn().equals(isbn)) {
                books.remove(book);
                return; // Retornar después de eliminar el libro
            }
        }
        // Si no se encontró, informa con una excepción clara
        throw new NotFoundException("El libro con el isbn " + isbn + " no pudo ser borrado");
    }
}
