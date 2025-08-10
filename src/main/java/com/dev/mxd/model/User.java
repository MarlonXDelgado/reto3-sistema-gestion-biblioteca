package com.dev.mxd.model;

import java.time.LocalDate;

// Clase que representa a un usuario del sistema.
// Un usuario puede pedir prestados libros y se identifica por su ID.
public class User {
    // Identificador único del usuario
    private String id;
    // Nombre completo del usuario
    private String name;
    // Correo electrónico del usuario
    private String email;
    // Fecha en la que el usuario quedó registrado en el sistema
    private LocalDate registerDate;

    // Crea un usuario con fecha de registro "hoy".
    // Útil cuando no se necesita indicar una fecha específica.
    public User(String id, String name, String email) {
        this(id, name, email, LocalDate.now());
    }

    // Crea un usuario indicando todos los datos, incluida la fecha de registro.
    // Útil para cargar usuarios existentes.
    public User(String id, String name, String email, LocalDate registerDate) {
        this.id = id;// guarda el ID tal cual lo recibimos
        this.name = name;// guarda el nombre
        this.email = email;// guarda el email
        this.registerDate = registerDate;// guardamos la fecha de registro
    }

    // Devuelve el ID del usuario
    public String getId() {
        return id;
    }

    // Devuelve el nombre del usuario
    public String getName() {
        return name;
    }

    // Permite actualizar el nombre del usuario
    public void setName(String name) {	
        this.name = name;
    }

    // Devuelve el email del usuario
    public String getEmail() {
        return email;
    }

    // Permite actualizar el email del usuario
    public void setEmail(String email) {
        this.email = email;
    }

    // Devuelve la fecha en que se registró el usuario
    public LocalDate getRegisterDate() {
        return registerDate;
    }

    
}
