# IT342_G5_Vargas_Lab1 - User Registration and Authentication System

A complete User Registration and Authentication system with a Spring Boot backend and React frontend, featuring a Candyland-themed design.

##  Project Overview

This laboratory session implements the core backend and web application of a User Registration and Authentication system. The application features:

- **Backend**: Spring Boot REST API with MySQL database and BCrypt password encryption
- **Frontend**: React web application with Candyland theme (pink, purple, blue color scheme)
- **Mobile**: To be implemented in Lab 2

##  Project Structure

\\\
IT342_G5_Vargas_Lab1/
 backend/                 # Spring Boot application
    src/
    pom.xml             # Maven configuration
    README.md           # Backend documentation
 web/                     # React application
    src/
    public/
    package.json        # npm configuration
 mobile/                  # Mobile app (Lab 2)
 docs/                    # Documentation
 README.md              # This file
 TASK_CHECKLIST.md      # Detailed task tracking
\\\

##  Quick Start

### Prerequisites
- Java 21+
- Node.js 16+ and npm
- MySQL 5.7+
- Git

### Backend Setup

\\\ash
cd backend

# Build
mvn clean build

# Run
mvn spring-boot:run
\\\

Backend runs on: **http://localhost:8081**

### Frontend Setup

\\\ash
cd web

# Install dependencies
npm install

# Run development server
npm start
\\\

Frontend runs on: **http://localhost:3000**

##  API Documentation

### Authentication Endpoints

#### Register
- **URL**: POST /api/auth/register
- **Body**: 
  \\\json
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "securepassword"
  }
  \\\
- **Response**: User object with auth token

#### Login
- **URL**: POST /api/auth/login
- **Body**:
  \\\json
  {
    "email": "john@example.com",
    "password": "securepassword"
  }
  \\\
- **Response**: User object with auth token

#### Get Current User (Protected)
- **URL**: GET /api/user/me
- **Headers**: Authorization: Bearer {token}
- **Response**: User profile information

##  Frontend Features

### Pages
1. **Register Page** - Create new account with validation
2. **Login Page** - Authenticate existing users
3. **Dashboard** - Protected page showing user profile

### Candyland Theme
- Primary Pink: #FF69B4
- Purple: #9D4EDD
- Blue: #3A86FF
- Yellow: #FFB703
- Mint Green: #06D6A0
- Cream Background: #FFF5E6

### Design Features
- Rounded corners and soft shadows
- Smooth animations and transitions
- Responsive design for mobile and desktop
- Comic Sans font for playful Candyland feel

##  Security Features

- **Password Encryption**: BCrypt hashing algorithm
- **CORS**: Enabled for http://localhost:3000
- **Protected Routes**: Dashboard accessible only after login
- **Token-based Authentication**: JWT-style tokens

##  Database

### Configuration
- **Host**: 127.0.0.1
- **Port**: 3306
- **Database**: user_auth_db
- **User**: root
- **Password**: (empty)

### Schema
\\\sql
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);
\\\

##  Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.x
- **Language**: Java 21
- **Build**: Maven
- **Database**: MySQL with Hibernate ORM
- **Security**: Spring Security with BCrypt
- **API**: RESTful with JSON

### Frontend
- **Library**: React 18.2.x
- **Styling**: CSS3 with animations
- **State**: React Hooks
- **HTTP**: Fetch API
- **Build**: webpack with react-scripts

##  Database Diagram

\\\

   users     

 id (PK)     
 firstName   
 lastName    
 email (UQ)  
 password    

\\\

##  Testing

### Manual Testing Steps

1. **Register a New User**
   - Navigate to http://localhost:3000
   - Click "Register here"
   - Fill form with test data
   - Click Register

2. **Login**
   - Enter registered email and password
   - Click Login

3. **View Dashboard**
   - After successful login, see user profile
   - Click Logout to return to login page

##  Checklist

See [TASK_CHECKLIST.md](TASK_CHECKLIST.md) for detailed task tracking and progress.

##  Workflow

### Development
\\\ash
# Terminal 1: Backend
cd backend
mvn spring-boot:run

# Terminal 2: Frontend
cd web
npm start
\\\

### Building for Production
\\\ash
# Backend
cd backend
mvn clean package

# Frontend
cd web
npm run build
\\\

##  Documentation

- Backend: See ackend/README.md
- Frontend: See web/README.md
- Full FRS with diagrams: See docs/FRS.pdf

##  Team

- **Name**: G5_Vargas
- **Course**: IT342 - Web Application Development
- **Lab**: Laboratory 1 - User Registration and Authentication System

##  License

Educational Project - IT342

##  Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [BCrypt Security](https://en.wikipedia.org/wiki/Bcrypt)

---

**Last Updated**: February 9, 2026
**Status**: Lab 1 In Progress - Frontend Complete
