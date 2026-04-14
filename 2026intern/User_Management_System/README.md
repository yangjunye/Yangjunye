# User Management System

A simple RESTful API for managing users, built with **Spring Boot** and **MySQL**.  
This project demonstrates basic CRUD operations, REST API design, and database interaction using **JdbcTemplate**.

## ✨ Features

- ✅ Create a new user
- ✅ Retrieve user by ID
- ✅ Retrieve all users
- ✅ Update user information
- ✅ Delete user
- ✅ Parameter validation (email format, etc.)
- ✅ Pagination support
- ✅ Fuzzy search by username

## 🛠️ Tech Stack

- Java 21
- Spring Boot 4.0.2
- MySQL 8.0
- JdbcTemplate
- Maven

## 📁 API Endpoints

| Method | Endpoint           | Description              |
|--------|--------------------|--------------------------|
| POST   | `/users`           | Create a new user        |
| GET    | `/users`           | Get all users            |
| GET    | `/users/{id}`      | Get user by ID           |
| PUT    | `/users/{id}`      | Update user by ID        |
| DELETE | `/users/{id}`      | Delete user by ID        |
| GET    | `/users/page`      | Pagination query         |
| GET    | `/users/search`    | Fuzzy search by username |

## 📊 Response Format

All endpoints return JSON in the following format:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
