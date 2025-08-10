package com.dev.mxd.model;
// Clase que representa un libro dentro del sistema
// Guarda datos básicos como ISBN, título y autor
public class Book {
    // Código único que identifica el libro 
    private String isbn;
    // Nombre del libro
    private String title;
    // Nombre del autor del libro
    private String author;

    // Constructor: se usa para crear un libro nuevo con sus datos básicos
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
    // Devuelve el ISBN del libro
    public String getIsbn() {
        return isbn;
    }
    // Devuelve el título del libro
    public String getTitle() {
        return title;
    }
     // Devuelve el autor del libro
    public String getAuthor() {
        return author;
    }   
}