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

    public Book(String isbn2, String title2, String author2) {
        //TODO Auto-generated constructor stub
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