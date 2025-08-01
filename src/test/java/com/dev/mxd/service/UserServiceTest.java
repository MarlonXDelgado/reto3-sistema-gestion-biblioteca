package com.dev.mxd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.User;

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
        var name = "Test User";
        var email = "test@example.com";

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
        var name = "Test User";
        var email = "test@example.com";
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
    void testGetUserByIdNotFound() {
        // Given
        var nonExistentId = "999";

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.getUserById(nonExistentId);
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
    void testGetAllUsers() {
        // Given
        var id1 = "123";
        var id2 = "456";
        var name1 = "User 1";
        var name2 = "User 2";
        var email1 = "user1@example.com";
        var email2 = "user2@example.com";

        // When
        service.addUser(id1, name1, email1);
        service.addUser(id2, name2, email2);

        // Then
        var users = service.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetAllUsersWhenEmpty() {
        // Given - service starts empty
        
        // When
        var users = service.getAllUsers();
        
        // Then
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    void testMultipleUsersOperations() {
        // Given
        var id1 = "123";
        var id2 = "456";
        var name1 = "User 1";
        var name2 = "User 2";
        var email1 = "user1@example.com";
        var email2 = "user2@example.com";

        // When
        service.addUser(id1, name1, email1);
        service.addUser(id2, name2, email2);

        // Then
        var user1 = service.getUserById(id1);
        var user2 = service.getUserById(id2);
        
        assertNotNull(user1);
        assertNotNull(user2);
        assertEquals(name1, user1.getName());
        assertEquals(name2, user2.getName());
        assertEquals(email1, user1.getEmail());
        assertEquals(email2, user2.getEmail());
    }

    @Test
    void testDeleteUser() throws NotFoundException {
        // Given
        var id = "123";
        var name = "Test User";
        var email = "test@example.com";
        service.addUser(id, name, email);

        // When
        service.deleteUser(id);

        // Then
        assertThrows(NotFoundException.class, () -> {
            service.getUserById(id);
        });
    }

    @Test
    void testDeleteUserNotFound() {
        // Given
        var nonExistentId = "999";

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
} 