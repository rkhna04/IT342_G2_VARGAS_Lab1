# IT342_G2_VARGAS_Lab1 - Task Checklist

## DONE

### Backend Implementation
- [x] Created Spring Boot project with User authentication system
  - **Commit:** 6c9cf8d7
  - User model with firstName, lastName, email, password, phone, address, city, country
  - AuthController: `/api/auth/register` and `/api/auth/login`
  - UserController: `/api/user/me` (GET/PUT for profile management)
  - BCrypt password encryption and JWT authentication
  - MySQL database (lab1_db) with JPA/Hibernate

### Frontend Implementation  
- [x] React authentication system with full UI
  - **Commit:** 6c9cf8d7
  - Register and Login components with validation
  - Dashboard with Profile and Logout buttons
  - Profile page with edit functionality (all user fields)
  - Custom logout confirmation modal with Yes/No buttons
  - Minimalist Brown theme (professional color palette)
  - Responsive design with smooth animations

### Project Structure
- [x] Organized repository structure
  - **Commit:** (pending)
  - Created /mobile folder for future mobile app
  - Added .gitignore for build artifacts
  - Removed unnecessary files (target/, node_modules/, temp docs)

### Mobile (Android Kotlin)
- [x] Implemented Register, Login, Dashboard, Profile (protected), Logout
  - **Commit:** (pending)
  - Uses Retrofit + OkHttp + Coroutines
  - Stores `token` and `user` via `AuthManager` (SharedPreferences)
  - Connects to same backend API (`http://10.0.2.2:8081/api` in emulator)
  - Protected screens redirect to login when logged out

### Backend Finalization
- [x] Logout endpoint `/api/auth/logout` (stateless JWT logout)
  - **Commit:** (pending)
- [x] Consistent response wrapper `{ status, message, data }`
- [x] Validation for email format, password length (BCrypt hashing)

### Web Integration
- [x] Web `authService` updated to parse `{ status, message, data }`
  - **Commit:** (pending)
  - Persists `authToken` and `user` from payload

## IN-PROGRESS

### Documentation
- [ ] Take screenshots of BOTH Web and Mobile (Register, Login, Dashboard, Profile, Logout)
- [ ] Update FRS.md and export FRS.pdf under `/docs` with screenshots and any diagram revisions

## TODO

### Final Submission
- [ ] Update FRS PDF in /docs folder
- [ ] Commit and push all changes to GitHub
- [ ] Verify both backend and frontend run successfully
- [ ] Submit to MS Teams

## System Architecture

### Backend Stack
- Framework: Spring Boot 3.2.x
- Language: Java 21
- Build Tool: Maven
- Database: MySQL 5.7+
- ORM: Hibernate/JPA
- Security: Spring Security with BCrypt

### Frontend Stack
- Framework: React 18.2.x
- Styling: CSS with a custom theme (monospace fonts)
- State Management: React Hooks (useState, useContext)
- HTTP Client: Fetch API
- Build Tool: Node.js/npm (react-scripts)

### Database Schema
```
users table:
- id (BIGINT, PRIMARY, AUTO_INCREMENT)
- firstName, lastName, email (UNIQUE), password (BCRYPT)
- phone, address, city, country
```

### API Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Register new user | No |
| POST | /api/auth/login | Login user | No |
| GET | /api/user/me | Get user profile | Yes (JWT) |
| PUT | /api/user/me | Update user profile | Yes (JWT) |

## Current Theme: Minimalist Brown
- Primary: #8B6F47 (Warm Brown)
- Secondary: #5D4E37 (Dark Brown)
- Accent: #C17855 (Terracotta)
- Highlight: #D4A574 (Tan)
- Danger: #B85450 (Muted Red)
- Success: #6B8E23 (Olive Green)
- Background: #FAF8F5 (Warm Cream)

### Running the Application

**Start Backend** (from workspace root):
``Backend** (port 8081):
```bash
cd backend
mvn spring-boot:run
```

**Frontend** (port 3000):
```bash
cd web
npm install
npm start
```

**Database**: MySQL on 127.0.0.1:3306, database: `lab1_db`
---

**Last Updated:** February 9, 2026
**Status:** Lab 1 - Backend & Web Frontend Implementation
