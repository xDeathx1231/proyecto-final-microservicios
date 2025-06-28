# 🚀 Proyecto Final - Microservicios

**Repositorio oficial del Proyecto Final - Ciclo VI (Módulo 2).**  
Este proyecto implementa una arquitectura de microservicios con Spring Boot y Docker Compose para gestionar usuarios y cursos, orquestados mediante un servidor Eureka.

---

## 📌 Descripción

El sistema está compuesto por tres servicios principales:

- 🗂️ **service-eureka**: Servidor de descubrimiento Eureka.
- 👤 **usuario-service**: Microservicio para gestión de usuarios.
- 📚 **cursos-service**: Microservicio para gestión de cursos.

Todos los servicios están contenedorizados con Docker y utilizan una base de datos MySQL 8.

---

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.5**
- **Spring Cloud Eureka**
- **MySQL 8**
- **Docker y Docker Compose**

---

## ⚙️ Estructura del Proyecto

proyecto-final-microservicios/
├── service-eureka/
├── usuario-service/
├── cursos-service/
└── docker-compose.yml


---

## 🧭 Requisitos Previos

- Docker
- Docker Compose
- Java 17 (opcional para desarrollo local)
- Git

---

## 🏃‍♂️ Pasos para Ejecutar el Proyecto

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/xDeathx1231/proyecto-final-microservicios.git
cd proyecto-final-microservicios
