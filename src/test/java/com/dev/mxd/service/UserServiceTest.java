package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.User;

import java.time.LocalDate;

/**
 * Pruebas unitarias para UserService.
 * 
 * Se validan:
 * - Flujos correctos: agregar, obtener, listar, actualizar y eliminar usuarios.
 * - Casos de error: parámetros nulos/vacíos, usuarios no encontrados.
 * - Cobertura de sobrecarga de métodos con fecha de registro.
 */
class UserServiceTest {

    private UserService service;

    @BeforeEach
    void setUp() {
        // Antes de cada test iniciamos un servicio limpio en memoria
        service = new UserService();
    }

     /**
     * Verifica que se pueda agregar un usuario y recuperarlo correctamente.
     */
    @Test
    void testAddUser() {
        // Given
        var id = "123";
        var name = "Marlon Delgado";
        var email = "marlondev@gmail.com";

        // When
        service.addUser(id, name, email);

        // Then
        var user = service.getUserById(id);
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    /**
     * Verifica que se pueda obtener un usuario existente por su ID.
     */
    @Test
    void testGetUserById() throws NotFoundException {
        // Given
        var id = "123";
        var name = "Marlon Delgado";
        var email = "marlondev@gmail.com";
        service.addUser(id, name, email);

        // When
        var user = service.getUserById(id);

        // Then
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    /**
     * Verifica que al agregar múltiples usuarios se puedan listar correctamente.
     */
    @Test
    void testGetAllUsers() {
        // Given
        var id1 = "123";
        var id2 = "456";
        var name1 = "Marlon Delgado";
        var name2 = "Kevin Sanchez";
        var email1 = "marlondev@gmail.com";
        var email2 = "ksanchez@gmail.com";
        
        service.addUser(id1, name1, email1);
        service.addUser(id2, name2, email2);

        // When
        var users = service.getAllUsers();

        // Then
        assertEquals(2, users.size());
        assertEquals(id1, users.get(0).getId());
        assertEquals(id2, users.get(1).getId());
        assertEquals(name1, users.get(0).getName());
        assertEquals(name2, users.get(1).getName());
        assertEquals(email1, users.get(0).getEmail());
        assertEquals(email2, users.get(1).getEmail());
    }

    /**
     * Verifica que se pueda eliminar un usuario existente.
     */
    @Test
    void testDeleteUserById() throws NotFoundException {
        // Given
        var id = "123";
        var name = "Marlon Delgado";
        var email = "marlondev@gmail.com";
        service.addUser(id, name, email);

        // When
        service.deleteUser(id);

        // Then
        assertThrows(NotFoundException.class, () -> {
            service.getUserById(id);
        });
    }

     /**
     * Verifica que se pueda actualizar el email de un usuario existente.
     */
    @Test
    void testUpdateUserEmail() throws NotFoundException {
        // Given
        var id = "123";
        var name = "Marlon Delgado";
        var email = "marlondev@gmail.com";
        var emailUpdated = "marlon.delgado@gmail.com";
        service.addUser(id, name, email);

        // When
        service.updateUserEmail(id, emailUpdated);

        // Then
        var user = service.getUserById(id);
        assertEquals(emailUpdated, user.getEmail());
        assertEquals(name, user.getName());
    }

    /**
     * Verifica que se pueda actualizar el nombre de un usuario existente.
     */
    @Test
    void testUpdateUserName() throws NotFoundException {
        // Given
        var id = "123";
        var name = "Marlon Delgado";
        var email = "marlondev@gmail.com";
        var nameUpdated = "Marlon Xavier Delgado Ruiz";
        service.addUser(id, name, email);

        // When
        service.updateUserName(id, nameUpdated);

        // Then
        var user = service.getUserById(id);
        assertEquals(nameUpdated, user.getName());
        assertEquals(email, user.getEmail());
    }

    /**
     * Verifica que al buscar un usuario inexistente se lance NotFoundException.
     */
    @Test
    void testGetUserByIdNotFound() {
        // Given
        var id = "456";

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.getUserById(id);
        });
    }

     /**
     * Verifica que no se pueda agregar un usuario con todos los parámetros nulos.
     */
    @Test
    void testAddUserWithNullParameters() {
        // Given
        String id = null;
        String name = null;
        String email = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario con todos los parámetros vacíos.
     */
    @Test
    void testAddUserWithEmptyParameters() {
        // Given
        String id = "";
        String name = "";
        String email = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email);
        });
    }

      /**
     * Verifica que no se pueda buscar un usuario con ID nulo.
     */
    @Test
    void testGetUserByIdWithNullId() {
        // Given
        String id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.getUserById(id);
        });
    }

     /**
     * Verifica que no se pueda eliminar un usuario inexistente.
     */
    @Test
    void testDeleteUserNotFound() {
        // Given
        var nonExistentId = "123";

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.deleteUser(nonExistentId);
        });
    }

     /**
     * Verifica que no se pueda eliminar un usuario con ID nulo.
     */
    @Test
    void testDeleteUserWithNullId() {
        // Given
        String id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteUser(id);
        });
    }

    /**
     * Verifica que se pueda agregar un usuario con fecha de registro.
     */
    @Test
    void testAddUserWithDate() {
        // Given
        String id = "123";
        String name = "Marlon Delgado";
        String email = "marlondev@gmail.com";
        LocalDate registerDate = LocalDate.now();

        // When
        service.addUser(id, name, email, registerDate);

        // Then
        User user = service.getUserById(id);
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(registerDate, user.getRegisterDate());
    }

      /**
     * Verifica que no se pueda actualizar el email con parámetros nulos o vacíos.
     */
    @Test
    void testUpdateUserEmailWithNullParameters() {
        // Given
        String id = "123";
        String email = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserEmail(id, email);
        });
    }
    @Test
    void testUpdateUserEmailWithEmptyEmail() {
        // Given
        String id = "123";
        String email = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserEmail(id, email);
        });
    }

      /**
     * Verifica que no se pueda actualizar el nombre con parámetros nulos o vacíos.
     */
    @Test
    void testUpdateUserNameWithNullParameters() {
        // Given
        String id = null;
        String name = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserName(id, name);
        });
    }
    @Test
    void testUpdateUserNameWithEmptyName() {
        // Given
        String id = "123";
        String name = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserName(id, name);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * cuando todos los parámetros son nulos.
     */
    @Test
    void testAddUserWithDateWithNullParameters() {
        // Given
        String id = null;
        String name = null;
        String email = null;
        LocalDate registerDate = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

     /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * cuando los parámetros de texto están vacíos.
     */
    @Test
    void testAddUserWithDateWithEmptyParameters() {
        // Given
        String id = "";
        String name = "";
        String email = "";
        LocalDate registerDate = LocalDate.now();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

     /**
     * Verifica que actualizar el email falle si el ID es nulo.
     */
    @Test
    void testUpdateUserEmailWithNullIdOnly() {
        // Given
        String id = null;
        String email = "xaviidev@email.com";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserEmail(id, email);
        });
    }

     /**
     * Verifica que actualizar el email falle si el email es nulo.
     */
    @Test
    void testUpdateUserEmailWithNullEmailOnly() {
        // Given
        String id = "123";
        String email = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserEmail(id, email);
        });
    }

    /**
     * Verifica que actualizar el email falle si el email es nulo.
     */
    @Test
    void testUpdateUserNameWithNullIdOnly() {
        // Given
        String id = null;
        String name = "Xavier Ruiz";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserName(id, name);
        });
    }

    /**
     * Verifica que actualizar el nombre falle si el nombre es nulo.
     */
    @Test
    void testUpdateUserNameWithNullNameOnly() {
        // Given
        String id = "123";
        String name = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUserName(id, name);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario si el nombre es nulo.
     */
    @Test
    void testAddUserWithNullNameOnly() {
        // Given
        String id = "123";
        String name = null;
        String email = "marlondev@gmail.com";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email);
        });
    }

     /**
     * Verifica que no se pueda agregar un usuario si el email es nulo.
     */
    @Test
    void testAddUserWithNullEmailOnly() {
        // Given
        String id = "123";
        String name = "Marlon Delgado";
        String email = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email);
        });
    }

    
    /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * si el nombre es nulo.
     */
    @Test
    void testAddUserWithDateWithNullNameOnly() {
        // Given
        String id = "123";
        String name = null;
        String email = "marlondev@gmail.com";
        LocalDate registerDate = LocalDate.now();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * si el email es nulo.
     */
    @Test
    void testAddUserWithDateWithNullEmailOnly() {
        // Given
        String id = "123";
        String name = "Marlon Delgado";
        String email = null;
        LocalDate registerDate = LocalDate.now();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

      /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * si la fecha es nula.
     */
    @Test
    void testAddUserWithDateWithNullRegisterDateOnly() {
        // Given
        String id = "123";
        String name = "Marlon Delgado";
        String email = "marlondev@gmail.com";
        LocalDate registerDate = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario si el nombre está vacío.
     */
    @Test
    void testAddUserWithEmptyNameOnly() {
        // Given
        String id = "123";
        String name = "";
        String email = "marlondev@gmail.com";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email);
        });
    }

     /**
     * Verifica que no se pueda agregar un usuario si el email está vacío.
     */
    @Test
    void testAddUserWithEmptyEmailOnly() {
        // Given
        String id = "123";
        String name = "Marlon Delgado";
        String email = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * si el nombre está vacío.
     */
    @Test
    void testAddUserWithDateWithEmptyNameOnly() {
        // Given
        String id = "123";
        String name = "";
        String email = "marlondev@gmail.com";
        LocalDate registerDate = LocalDate.now();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

    /**
     * Verifica que no se pueda agregar un usuario con fecha de registro
     * si el email está vacío.
     */
    @Test
    void testAddUserWithDateWithEmptyEmailOnly() {
        // Given
        String id = "123";
        String name = "Marlon Delgado";
        String email = "";
        LocalDate registerDate = LocalDate.now();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(id, name, email, registerDate);
        });
    }

     /**
     * Verifica que se pueda obtener un usuario específico cuando
     * hay múltiples usuarios registrados.
     */
    @Test
    void testGetUserByIdWithMultipleUsers() {
        // Given
        service.addUser("123", "Marlon Delgado", "marlondev@gmail.com");
        service.addUser("456", "Kevin Sanchez", "ksanchez@gmail.com");
        service.addUser("789", "Xavier Ruiz", "xaviidev@email.com");

        // When
        User user = service.getUserById("456");

        // Then
        assertNotNull(user);
        assertEquals("456", user.getId());
        assertEquals("Kevin Sanchez", user.getName());
        assertEquals("ksanchez@gmail.com", user.getEmail());
    }
}
