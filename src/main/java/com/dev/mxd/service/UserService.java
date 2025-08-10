package com.dev.mxd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dev.mxd.exception.NotFoundException;
import com.dev.mxd.model.User;

// Servicio que maneja todo lo relacionado con los USUARIOS.
//  registrar nuevos usuarios, buscarlos y listar todos.
public class UserService {

    // Lista en memoria donde guardamos a todos los usuarios
    private List<User> users = new ArrayList<>();

    // Agrega un usuario con la fecha de registro actual
    public void addUser(String id, String name, String email) {
        // Validam que ningún dato sea nulo
        if (id == null || name == null || email == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        // Validam que no estén vacíos o con solo espacios
        if (id.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
        }
        // Si todo está correcto, lo agregam con la fecha actual
        users.add(new User(id, name, email));
    }

    // Agrega un usuario pero permitiendo especificar la fecha de registro
    public void addUser(String id, String name, String email, LocalDate registerDate) {
        if (id == null || name == null || email == null || registerDate == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (id.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
        }
        users.add(new User(id, name, email, registerDate));
    }

    // Devuelve la lista completa de usuarios
    public List<User> getAllUsers() {
        return users;
    }

    // Busca un usuario por su ID, si no lo encuentra lanza una excepción
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

    // Cambia el correo electrónico de un usuario
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

    // Cambia el nombre de un usuario
    public void updateUserName(String id, String name) throws NotFoundException {
        if (id == null || name == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        var user = getUserById(id);// Si no existe, lanza NotFoundException
        user.setName(name);// Actualiza el correo
    }

    // Elimina un usuario por su ID
    public void deleteUser(String id) throws NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        var user = getUserById(id);
        users.remove(user);
    }
}
