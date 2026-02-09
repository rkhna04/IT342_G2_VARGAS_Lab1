Got it! Here’s your **fully formatted GitHub-ready README** with proper Markdown styling—asterisks, headings, code blocks, everything ready to **copy-paste directly**:

```markdown
# IT342_G5_Vargas_Lab1 – User Registration and Authentication System

A full-stack **User Registration and Authentication System** built with a Spring Boot backend and a React frontend.

---

## Project Overview

This project implements the core backend services and web application for a User Registration and Authentication system. It demonstrates secure user management, RESTful API design, and frontend-backend integration.

### Key Features

- **Backend**: Spring Boot REST API with MySQL database and BCrypt password hashing  
- **Frontend**: React-based web application with a clean and responsive UI  
- **Mobile**: To be implemented in **Lab 2**  

---

## Project Structure

```

IT342_G5_Vargas_Lab1/
│
├── backend/                 # Spring Boot application
│   ├── src/
│   ├── pom.xml              # Maven configuration
│   └── README.md            # Backend documentation
│
├── web/                     # React application
│   ├── src/
│   ├── public/
│   └── package.json         # npm configuration
│
├── mobile/                  # Mobile application (Lab 2)
├── docs/                    # Project documentation
├── README.md                # Main project README
└── TASK_CHECKLIST.md        # Task tracking and progress

````

---

## Quick Start

### Prerequisites

- Java 21+  
- Node.js 16+ and npm  
- MySQL 5.7+  
- Git  

---

## Backend Setup

```bash
cd backend

# Build the project
mvn clean build

# Run the application
mvn spring-boot:run
````

Backend runs on: **[http://localhost:8081](http://localhost:8081)**

---

## Frontend Setup

```bash
cd web

# Install dependencies
npm install

# Run development server
npm start
```

Frontend runs on: **[http://localhost:3000](http://localhost:3000)**

---

## API Documentation

### Register User

* **Method**: POST
* **URL**: `/api/auth/register`
* **Request Body**:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

* **Response**: User object with authentication token

---

### Login User

* **Method**: POST
* **URL**: `/api/auth/login`
* **Request Body**:

```json
{
  "email": "john@example.com",
  "password": "securepassword"
}
```

* **Response**: User object with authentication token

---

### Get Current User (Protected)

* **Method**: GET
* **URL**: `/api/user/me`
* **Header**: `Authorization: Bearer {token}`
* **Response**: Authenticated user profile

---

## Frontend Features

* **Register Page** – User registration with input validation
* **Login Page** – Secure authentication
* **Dashboard** – Protected page showing user profile
* **Responsive UI** – Works on both desktop and mobile

---

## Security Features

* **Password Encryption** – BCrypt hashing
* **CORS** – Enabled for `http://localhost:3000`
* **Protected Routes** – Dashboard accessible only after authentication
* **Token-Based Authentication** – JWT-style tokens

---

## Database

### Configuration

* **Host**: 127.0.0.1
* **Port**: 3306
* **Database**: `lab1_db`
* **User**: root
* **Password**: (empty)

### Schema

```sql
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);
```

---

## Technology Stack

### Backend

* **Framework**: Spring Boot 3.2.x
* **Language**: Java 21
* **Build Tool**: Maven
* **Database**: MySQL with Hibernate ORM
* **Security**: Spring Security with BCrypt

### Frontend

* **Library**: React 18.2.x
* **Styling**: CSS3
* **State Management**: React Hooks
* **HTTP Requests**: Fetch API

---

## Database Diagram

```
users
--------------------
id (PK)
firstName
lastName
email (UQ)
password
```

---

## Testing

1. **Register a New User**

   * Navigate to [http://localhost:3000](http://localhost:3000)
   * Fill in the registration form
   * Submit to create a new user

2. **Login**

   * Enter registered email and password
   * Submit to authenticate

3. **Access Dashboard**

   * View profile after successful login
   * Logout to return to login page

---

## Workflow

### Development Mode

```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend
cd web
npm start
```

### Production Build

```bash
# Backend
cd backend
mvn clean package

# Frontend
cd web
npm run build
```

---

## Documentation

* **Backend Docs**: `backend/README.md`
* **Frontend Docs**: `web/README.md`
* **Full FRS & Diagrams**: `docs/FRS.pdf`

---

## Team

* **Group**: G2_Vargas
* **Course**: IT342 – Web Application Development

---

## License

**Educational Project – IT342**


Do you want me to do that?
```
