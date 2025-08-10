# Reto 3 - Sistema de Gestión de Biblioteca

Este proyecto es un **Sistema de Gestión de Biblioteca**.  
El sistema permite manejar libros, usuarios y préstamos, garantizando un flujo correcto en la gestión y validando todas las operaciones.  
Incluye un conjunto completo de **pruebas unitarias** con **JUnit 5** y **Mockito**, alcanzando el **100% de cobertura**.

---

##  Objetivo

Diseñar e implementar un sistema que permita:
- Registrar y consultar **usuarios**.
- Registrar y consultar **libros**.
- Gestionar **préstamos y devoluciones**.
- Aplicar **validaciones** y lanzar excepciones personalizadas en caso de datos inválidos o inexistentes.

---

## Características principales

- Programación Orientada a Objetos (POO).
- Separación por capas: **Modelos**, **Servicios**, **Excepciones** y **Tests**.
- Validaciones exhaustivas para datos nulos y vacíos.
- **Colecciones en memoria** para almacenar información.
- Uso de **Mockito** para simular dependencias en las pruebas.
- Cobertura de **100%** en instrucciones y ramas.

---

## Estructura del proyecto

```
src/
 ├── main/java/com/dev/mxd/
 │    ├── model/          # Clases de dominio (Book, User, Loan, LoanState)
 │    ├── service/        # Lógica de negocio (BookService, UserService, LoanService)
 │    ├── exception/      # Excepción personalizada (NotFoundException)
 │
 └── test/java/com/dev/mxd/
      ├── model/          # Pruebas de modelos
      ├── service/        # Pruebas de servicios con Mockito
```

---

## Requerimientos previos

- **Java 21** 
- **Maven 3.x**.
- **JUnit 5** y **Mockito** (incluidos en el `pom.xml`).
- IDE recomendado: VS code

---

## Instalación y ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuusuario/reto3-sistema-gestion-biblioteca.git
   cd reto3-sistema-gestion-biblioteca
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

3. Ejecutar la aplicación 
   ```bash
   mvn exec:java -Dexec.mainClass="com.dev.mxd.Main"
   ```

---

##  Ejecutar las pruebas

Para correr todas las pruebas:
```bash
mvn test
```

Generar el reporte de cobertura:
```bash
mvn test jacoco:report
```

---

## Cobertura de código

El proyecto alcanza **100% de cobertura** en instrucciones y ramas, garantizando que todos los escenarios están cubiertos.

![Reporte de cobertura](WhatsApp%20Image%202025-08-09%20at%2010.50.31%20AM.jpeg)

---

## ✍ Autor

- **Marlon Xavier Delgado Ruiz**
- **Kevin Esteban Sanchez Mendez**
