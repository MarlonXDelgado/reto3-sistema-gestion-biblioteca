package com.dev.mxd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.User;

public class UserService {

    private List<User> users = new ArrayList<>();

    public void addUser(String id, String name, String email) {
        users.add(new User(id, name, email));
    }

    public void addUser(String id, String name, String email, LocalDate registerDate) {
        users.add(new User(id, name, email, registerDate));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String id) throws NotFoundException {
        for (var user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new NotFoundException("El usuario con el id " + id + " no fue encontrado");
    }

    public void updateUserEmail(String id, String email) throws NotFoundException {
        var user = getUserById(id);
        user.setEmail(email);
    }

    public void updateUserName(String id, String name) throws NotFoundException {
        var user = getUserById(id);
        user.setName(name);
    }

    public void deleteUser(String id) throws NotFoundException {
        var user = getUserById(id);
        users.remove(user);
    }
}
