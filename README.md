# 📝 Notes App (Spring Boot + JWT)

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)
![JWT](https://img.shields.io/badge/Security-JWT-orange)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A simple **Notes Management Application** built with **Spring Boot**.  
It provides **user authentication (JWT)** and full **CRUD operations** on notes.  
Designed as part of an internship assignment to demonstrate backend development skills.  

---

## 🚀 Features
- 🔐 User Registration & Login (JWT Authentication)  
- 🗂️ Create, Read, Update, Delete Notes  
- 👤 User-specific notes (no one else can see your notes)  
- ⚡ Optimistic Locking (prevents version conflicts during updates)  
- 🛡️ Password encryption using Spring Security  

---

## 🛠️ Tech Stack
- **Java 17+**  
- **Spring Boot** (Web, Security, JPA, Validation)  
- **MySQL / H2** (configurable database)  
- **JWT (JSON Web Token)** for authentication  
- **Maven** for dependency management  

---

## 📌 API Endpoints

### 🔑 Authentication
| Method | Endpoint             | Description          |
|--------|----------------------|----------------------|
| POST   | `/api/auth/register` | Register a new user  |
| POST   | `/api/auth/login`    | Login & get JWT token |

### 📝 Notes
| Method | Endpoint          | Description                  |
|--------|-------------------|------------------------------|
| GET    | `/api/notes`      | Get all notes (user-specific)|
| POST   | `/api/notes`      | Create a new note            |
| PUT    | `/api/notes/{id}` | Update a note (with version) |
| DELETE | `/api/notes/{id}` | Delete a note                |

---

## 🧑‍💻 Example Requests

### Register
```json
POST /api/auth/register
{
  "username": "alice",
  "password": "mypassword"
}

