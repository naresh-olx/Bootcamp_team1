# 🚗 BootCamp Project - Team 1

A backend project built using **Spring Boot**, focused on secure and scalable REST API development for managing user authentication and inventory data. This project is designed with a strong emphasis on clean architecture, modular design, and real-world patterns like **DTOs**, **mappers**, **repository layers**, and **JWT-based authentication**.

---

# Prerequisites:
- Java 21
- Maven
- MongoDB running locally or via URI

---

# Steps:
- git clone [Repo](https://github.com/naresh-olx/Bootcamp_team1.git)
- cd Bootcamp_team1
- mvn clean install
- mvn spring-boot:run

---

## 🧠 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **MongoDB**
- **Lombok**
- **DTO + Mapper Pattern**
- **Global Exception Handling**
- **Validation (Jakarta)**

---

## 🔐 Authentication Flow

We use **Spring Security + JWT** to manage secure login and signup functionalities.

### 🔁 JWT Flow:
1. **User Signup** → data saved to MongoDB.
2. **User Login** → validated credentials generate JWT.
3. **JWT Token** is required in the `Authorization` header for all secured endpoints.
4. All protected APIs extract and validate the token from the request header using a custom `JwtFilter`.

---

## 🧾 APIs Overview

### 🧍‍♂️ User APIs

| Method | Endpoint                  | Description        |
|--------|---------------------------|--------------------|
| POST   | `/api/v1/users/signup`    | Register a new user |
| POST   | `/api/v1/users/login`     | Login and receive JWT token |

---

### 📦 Inventory APIs

🔐 Requires a valid JWT token (sent via `Authorization: Bearer <token>` header)

| Method | Endpoint                             | Description                     |
|--------|--------------------------------------|---------------------------------|
| POST   | `/api/v1/inventories/add`            | Create a new inventory item     |
| GET    | `/api/v1/inventories`                | Get all inventories for the user (paginated) |
| GET    | `/api/v1/inventories/{sku}`          | Get inventory by SKU (only if created by the user) |
| PUT    | `/api/v1/inventories/{sku}`          | Update inventory (ownership check) |

---

## 🏗️ Project Structure & Design

### Layers:

Controller → Service → Mapper → Repository → MongoDB


### Key Concepts:

- **DTOs (Data Transfer Objects):** Used to decouple internal entity structures from API contracts.
- **Mappers:** Handle conversions between DTOs and Entities.
- **Repositories:** Interface-based access to MongoDB using Spring Data.
- **SecurityConfig:** Configures Spring Security with stateless JWT-based auth.
- **JwtFilter:** Intercepts and validates tokens for every request.
- **GlobalExceptionHandler:** Catches and formats all API errors consistently.
- **Validation:** Jakarta Validation (e.g., `@NotBlank`, `@NotNull`, `@Positive`) for request payload validation.

---

## 🧪 Error Handling

All exceptions are caught and returned in a consistent format:

```json
{
  "errorCode": "BAD_REQUEST",
  "errorMessage": "VIN is required"
}
```

---

## 🔒 Secured Endpoints
All inventory APIs are secured and only accessible by authenticated users. Authorization is handled via JWT tokens — only the creator of an inventory item can view/update it.

---

## 👨‍💻 Team Members

- Nirbhay
- Yash
- Naresh
- Naina

---

## 🏁 Future Enhancements

- Role-based authorization (Admin/User)
- Inventory image upload
- Inventory status transitions with logs
- Audit trail for create/update actions

---
### Made with ❤️ by Team 1 - BootCamp Project



