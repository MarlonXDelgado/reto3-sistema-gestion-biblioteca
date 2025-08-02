package com.dev.mxd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.User;

public class UserService {

    private List<User> users = new ArrayList<>();

    public void addUser(String id, String name, String email) {
        if (id == null || name == null || email == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (id.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
        }
        users.add(new User(id, name, email));
    }

    public void addUser(String id, String name, String email, LocalDate registerDate) {
        if (id == null || name == null || email == null || registerDate == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (id.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
        }
        users.add(new User(id, name, email, registerDate));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String id) throws NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        for (var user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new NotFoundException("El usuario con el id " + id + " no fue encontrado");
    }

    public void updateUserEmail(String id, String email) throws NotFoundException {
        if (id == null || email == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        var user = getUserById(id);
        user.setEmail(email);
    }

    public void updateUserName(String id, String name) throws NotFoundException {
        if (id == null || name == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        var user = getUserById(id);
        user.setName(name);
    }

    public void deleteUser(String id) throws NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        var user = getUserById(id);
        users.remove(user);
    }
}
