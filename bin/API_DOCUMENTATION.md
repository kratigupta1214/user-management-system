# API Documentation — Spring Boot User Management API

**Base URL:** `http://localhost:8080/api`  
**Content-Type:** `application/json`

All successful responses use the `ApiResponse` envelope:

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { },
  "timestamp": "2026-05-18T10:30:00"
}
```

Error responses use the `ErrorResponse` envelope:

```json
{
  "success": false,
  "message": "Validation failed",
  "status": 400,
  "path": "/api/users",
  "timestamp": "2026-05-18T10:30:00",
  "errors": {
    "email": "Email must be a valid address"
  }
}
```

---

## Endpoints

### 1. Create User

| | |
|---|---|
| **Method** | `POST` |
| **URL** | `/users` |
| **Status** | `201 Created` |

**Request body:**

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+919999888877",
  "dateOfBirth": "1990-05-20",
  "active": true
}
```

| Field | Type | Required | Rules |
|-------|------|----------|-------|
| firstName | string | Yes | 2–50 chars |
| lastName | string | Yes | 2–50 chars |
| email | string | Yes | Valid email, max 100 |
| phone | string | No | 10–15 digits, optional `+` |
| dateOfBirth | date | No | Must be in the past |
| active | boolean | No | Default `true` |

**Example response:**

```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": 11,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+919999888877",
    "dateOfBirth": "1990-05-20",
    "active": true,
    "createdAt": "2026-05-18T10:00:00",
    "updatedAt": "2026-05-18T10:00:00"
  },
  "timestamp": "2026-05-18T10:00:01"
}
```

---

### 2. Get All Users (Pagination & Sorting)

| | |
|---|---|
| **Method** | `GET` |
| **URL** | `/users` |
| **Status** | `200 OK` |

**Query parameters:**

| Param | Default | Description |
|-------|---------|-------------|
| page | `0` | Zero-based page index |
| size | `10` | Page size (max 100) |
| sortBy | `id` | `id`, `firstName`, `lastName`, `email`, `createdAt`, `updatedAt` |
| direction | `asc` | `asc` or `desc` |

**Example:** `GET /users?page=0&size=5&sortBy=lastName&direction=asc`

**Example response:**

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {
    "users": [ { "id": 1, "firstName": "Priya", "lastName": "Sharma", "email": "priya.sharma@example.com" } ],
    "page": 0,
    "size": 5,
    "totalElements": 10,
    "totalPages": 2,
    "first": true,
    "last": false
  },
  "timestamp": "2026-05-18T10:05:00"
}
```

---

### 3. Get User By ID

| | |
|---|---|
| **Method** | `GET` |
| **URL** | `/users/{id}` |
| **Status** | `200 OK` / `404 Not Found` |

**Example:** `GET /users/1`

---

### 4. Update User

| | |
|---|---|
| **Method** | `PUT` |
| **URL** | `/users/{id}` |
| **Status** | `200 OK` / `404` / `409 Conflict` (duplicate email) |

**Request body (all fields optional):**

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@example.com",
  "phone": "9876543210",
  "dateOfBirth": "1992-01-01",
  "active": false
}
```

---

### 5. Delete User

| | |
|---|---|
| **Method** | `DELETE` |
| **URL** | `/users/{id}` |
| **Status** | `200 OK` / `404 Not Found` |

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | Success |
| 201 | User created |
| 400 | Validation or bad request |
| 404 | User not found |
| 409 | Duplicate email |
| 500 | Unexpected server error |

---

## Sample cURL Commands

```bash
# Create user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"test.user@example.com\",\"phone\":\"9998887776\"}"

# Get all (page 0, size 10)
curl "http://localhost:8080/api/users?page=0&size=10&sortBy=createdAt&direction=desc"

# Get by id
curl http://localhost:8080/api/users/1

# Update
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d "{\"lastName\":\"Updated\"}"

# Delete
curl -X DELETE http://localhost:8080/api/users/1
```

---

## Dummy Data (Seeded on Startup)

On first run, `data.sql` inserts 10 sample users (e.g. Priya Sharma, Rahul Verma, James Wilson). Emails are unique; re-runs skip duplicates.

See [README.md](README.md) for setup and Postman import instructions.
