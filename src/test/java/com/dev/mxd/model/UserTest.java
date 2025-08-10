package com.dev.mxd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para el modelo User.
 * Valida que el constructor y los getters funcionen correctamente,
 * asegurando que los datos del usuario se asignen de forma adecuada.
 */
public class UserTest {
  /**
     * Verifica que el constructor asigne correctamente
     * el id, nombre y email, y que los getters los devuelvan sin alteraciones.
     */
    @Test
  void constructorYGetters() {
    // Given: datos de un usuario
    var u = new User("u1", "Ana", "ana@mail.com");

    // Then: se espera que los getters retornen exactamente lo que se pasó al constructor
    assertEquals("u1", u.getId());
    assertEquals("Ana", u.getName());
    assertEquals("ana@mail.com", u.getEmail());
  }
}
