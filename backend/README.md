# Backend - Spring Boot Application

This is the backend REST API for the User Registration and Authentication system.

## Technology Stack

- **Framework**: Spring Boot 2.7.12
- **Language**: Java 11
- **Build Tool**: Maven
- **Database**: MySQL 8.x
- **Security**: BCrypt password hashing + JWT tokens

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL 5.7+ running on `localhost:3306`

## Database Configuration

The application expects a MySQL database with the following configuration:

```properties
Database: lab1_db
Host: 127.0.0.1
Port: 3306
Username: root
Password: (empty)
```

The database schema will be automatically created by Hibernate on first run.

## Running the Application

### Option 1: Using Maven (from project root)
```bash
cd backend
mvn spring-boot:run
```

### Option 2: Using the pre-built JAR
```bash
cd backend
java -jar target/lab1-backend-1.0.0.jar
```

### Option 3: Using the batch file (Windows)
From the project root directory:
```bash
run-backend.bat
```

The backend server will start on **http://localhost:8081**

## API Endpoints

### Authentication

#### Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Response**: User object with JWT token

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Response**: User object with JWT token

### User Profile (Protected)

#### Get Current User
```http
GET /api/user/me
Authorization: Bearer {your-jwt-token}
```

**Response**: User profile information

## Configuration

All configuration is in [`src/main/resources/application.properties`](./src/main/resources/application.properties):

- Server port: `8081`
- Database connection details
- JWT secret key
- CORS allowed origins

## Building

To rebuild the application:

```bash
cd backend
mvn clean package
```

This creates a new JAR file in the `target/` directory.

## Troubleshooting

### Database Connection Issues

1. Ensure MySQL is running:
   ```bash
   mysql -u root -p
   ```

2. Check if the database exists:
   ```sql
   SHOW DATABASES;
   ```

3. If needed, create it manually:
   ```sql
   CREATE DATABASE lab1_db;
   ```

### Port Already in Use

If port 8081 is already in use, change it in `application.properties`:
```properties
server.port=8082
```

Don't forget to update the frontend's API URL accordingly.

## Security Notes

- Passwords are hashed using BCrypt before storage
- JWT tokens are issued on successful login/registration
- The `/api/user/me` endpoint requires a valid JWT token
- CORS is enabled for `http://localhost:3000` (React frontend)
