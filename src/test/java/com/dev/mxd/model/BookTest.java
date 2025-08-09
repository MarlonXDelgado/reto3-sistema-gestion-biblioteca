package com.dev.mxd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BookTest {
     @Test
    void constructorAndGetters() {
        var b = new Book("111", "Clean Code", "Robert C. Martin");
        assertEquals("111", b.getIsbn());
        assertEquals("Clean Code", b.getTitle());
        assertEquals("Robert C. Martin", b.getAuthor());
    }
    
}
