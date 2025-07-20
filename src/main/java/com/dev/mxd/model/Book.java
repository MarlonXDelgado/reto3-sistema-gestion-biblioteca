package com.dev.mxd.model;

public class Book {
    private Integer isbn;
    private String title;
    private String author;
    
    public Book(Integer isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }   
}