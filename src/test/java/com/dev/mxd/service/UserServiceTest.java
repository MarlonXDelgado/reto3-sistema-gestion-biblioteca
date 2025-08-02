package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;

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
}
