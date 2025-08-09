package com.dev.mxd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
  void constructorYGetters() {
    var u = new User("u1", "Ana", "ana@mail.com");
    assertEquals("u1", u.getId());
    assertEquals("Ana", u.getName());
    assertEquals("ana@mail.com", u.getEmail());
  }
}
