# User Management System

A **production-style Spring Boot REST API** for managing users with full CRUD, pagination, sorting, validation, DTOs, and global exception handling. Built as a **portfolio-ready** backend project suitable for Fiverr, Upwork, and technical interviews.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## Project Overview

The **User Management System** exposes a clean REST API to create, read, update, and delete users stored in MySQL. The codebase follows **layered architecture** (Controller → Service → Repository), separates **entities from DTOs**, and returns consistent JSON envelopes via `ResponseEntity` and a global exception handler.

**Key capabilities:**

- Create, update, delete, and fetch users by ID  
- Paginated and sortable user listing  
- Bean Validation on request DTOs  
- Duplicate email detection (409 Conflict)  
- JPA auditing (`createdAt`, `updatedAt`)  
- Sample seed data for instant demos  
- Docker & Docker Compose support  
- Postman collection included  

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Language |
| Spring Boot 3.2 | Application framework |
| Spring Data JPA | Persistence |
| MySQL 8 | Relational database |
| Maven | Build & dependency management |
| Lombok | Boilerplate reduction |
| Jakarta Validation | Request validation |
| Docker | Containerized deployment |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Client (Postman / Frontend)              │
└─────────────────────────────┬───────────────────────────────┘
                              │ HTTP JSON
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  Controller Layer    UserController (@RestController)      │
│  - Validation (@Valid)  - ResponseEntity<ApiResponse<>>    │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  Service Layer       UserService / UserServiceImpl           │
│  - Business rules    - Pagination & duplicate checks         │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  Repository Layer    UserRepository (JpaRepository)          │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  Database            MySQL (users table)                     │
└─────────────────────────────────────────────────────────────┘

        GlobalExceptionHandler  ←  cross-cutting error responses
        UserMapper              ←  Entity ↔ DTO mapping
```

**Design decisions:**

- **DTOs** prevent exposing JPA entities and allow independent API contracts.  
- **Service interface** demonstrates testability and clear boundaries.  
- **Global exception handler** ensures uniform error JSON for clients.  
- **`open-in-view=false`** avoids lazy-loading issues in REST APIs.  

---

## Folder Structure

```
user-management-system/
├── src/main/java/com/usermanagement/
│   ├── UserManagementApplication.java    # Main entry + JPA auditing
│   ├── config/                           # Web/CORS configuration
│   ├── controller/                       # REST endpoints
│   ├── dto/
│   │   ├── request/                      # CreateUserRequest, UpdateUserRequest
│   │   └── response/                     # UserResponse, ApiResponse, PagedUserResponse
│   ├── entity/                           # User JPA entity
│   ├── exception/                        # Custom exceptions + GlobalExceptionHandler
│   ├── mapper/                           # Entity ↔ DTO mapping
│   ├── repository/                       # Spring Data JPA
│   └── service/
│       └── impl/                         # Business logic implementation
├── src/main/resources/
│   ├── application.properties            # MySQL & JPA configuration
│   └── data.sql                          # Sample dummy users
├── postman/
│   └── User-Management-System.postman_collection.json
├── API_DOCUMENTATION.md                  # Detailed API reference
├── Dockerfile                            # Multi-stage container build
├── docker-compose.yml                    # MySQL + API stack
├── pom.xml
└── README.md
```

---

## Prerequisites

- **JDK 17** ([Adoptium](https://adoptium.net/) or Oracle JDK)  
- **Maven 3.8+**  
- **MySQL 8.0+** (local install or Docker)  
- **Postman** (optional, for API testing)  
- **Docker** (optional, for containerized run)  

---

## MySQL Setup

### Option A — Local MySQL

1. Install and start MySQL Server.  
2. Create database (optional; app can auto-create):

```sql
CREATE DATABASE IF NOT EXISTS user_management_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

3. Update credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### Option B — Docker MySQL only

```bash
docker run --name ums-mysql -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=user_management_db -p 3306:3306 -d mysql:8.0
```

---

## Setup Instructions

1. **Clone or download** this project.

2. **Configure database** in `application.properties` (see above).

3. **Build the project:**

```bash
cd user-management-system
mvn clean install
```

4. **Run the application:**

```bash
mvn spring-boot:run
```

The API starts at: **http://localhost:8080/api**

5. **Import Postman collection:**  
   `postman/User-Management-System.postman_collection.json`  
   Set variable `baseUrl` = `http://localhost:8080/api`

---

## Run with Docker Compose

Runs MySQL and the API together:

```bash
docker compose up --build
```

API: **http://localhost:8080/api/users**

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users` | Create a new user |
| `GET` | `/api/users` | Get all users (paginated, sortable) |
| `GET` | `/api/users/{id}` | Get user by ID |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

**Pagination example:**  
`GET /api/users?page=0&size=5&sortBy=lastName&direction=asc`

Full details: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

---

## Example Requests

### Create user

```json
POST /api/users
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+919999888877",
  "dateOfBirth": "1990-05-20",
  "active": true
}
```

### Realistic dummy data (auto-seeded)

| Name | Email |
|------|-------|
| Priya Sharma | priya.sharma@example.com |
| Rahul Verma | rahul.verma@example.com |
| James Wilson | james.wilson@example.com |
| Sarah Johnson | sarah.johnson@example.com |

Ten users are inserted on startup via `data.sql` (idempotent).

---

## Screenshots

> Add screenshots here for your portfolio (Postman, API responses, database).

| Description | Placeholder |
|-------------|-------------|
| Postman — Create User | `![Create User](docs/screenshots/create-user.png)` |
| Postman — Paginated List | `![Get All Users](docs/screenshots/get-all-users.png)` |
| MySQL — users table | `![Database](docs/screenshots/mysql-users.png)` |
| Validation error 400 | `![Validation](docs/screenshots/validation-error.png)` |

Create a `docs/screenshots/` folder and replace paths after capturing images.

---

## Testing the API

```bash
curl http://localhost:8080/api/users?page=0&size=5
curl http://localhost:8080/api/users/1
```

Run unit tests:

```bash
mvn test
```

---

## Configuration Reference

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | `8080` | HTTP port |
| `server.servlet.context-path` | `/api` | API prefix |
| `spring.datasource.url` | localhost MySQL | JDBC URL |
| `spring.jpa.hibernate.ddl-auto` | `update` | Schema management |
| `app.pagination.max-size` | `100` | Max page size |

---

## Future Improvements

- [ ] JWT-based authentication & role-based access (ADMIN/USER)  
- [ ] Spring Security integration  
- [ ] OpenAPI / Swagger UI (`springdoc-openapi`)  
- [ ] Integration tests with Testcontainers  
- [ ] Flyway/Liquibase for versioned migrations  
- [ ] Redis caching for frequent reads  
- [ ] Soft delete & audit trail  
- [ ] Email verification workflow  
- [ ] CI/CD pipeline (GitHub Actions)  
- [ ] Kubernetes deployment manifests  

---

## Author & Portfolio

Built as a **professional REST API sample** demonstrating enterprise patterns: layered design, DTOs, validation, pagination, exception handling, and deployment readiness.

**Skills demonstrated:** Spring Boot, JPA, MySQL, REST, Maven, Docker, clean code, API documentation.

---

## License

MIT License — free to use for learning, portfolios, and client demos.
