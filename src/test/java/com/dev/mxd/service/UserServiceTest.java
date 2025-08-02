package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.User;

import java.time.LocalDate;

class UserServiceTest {

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService();
    }

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

    @Test
    void testGetUserByIdNotFound() {
        // Given
        var id = "456";

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.getUserById(id);
        });
    }

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

    @Test
    void testGetUserByIdWithNullId() {
        // Given
        String id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.getUserById(id);
        });
    }

    @Test
    void testDeleteUserNotFound() {
        // Given
        var nonExistentId = "123";

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.deleteUser(nonExistentId);
        });
    }

    @Test
    void testDeleteUserWithNullId() {
        // Given
        String id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteUser(id);
        });
    }

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
