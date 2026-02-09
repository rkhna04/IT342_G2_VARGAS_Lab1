# IT342_G5_Vargas_Lab1 - Task Checklist

## DONE

### Backend Implementation
- [x] Created Spring Boot project structure with proper configuration
  - **Commit:** 4ebeb5e
  - Updated User model to use firstName and lastName fields
  - Enhanced AuthController with proper JSON responses
  - Created DTOs: LoginResponse, UserDTO, RegisterRequest

### Frontend Implementation  
- [x] Created Candyland-themed React application
  - **Commit:** To be committed
  - Implemented Register component with validation
  - Implemented Login component with authentication
  - Implemented Dashboard/Profile component
  - Implemented ProtectedRoute component
  - Created API service layer for backend communication

### Database
- [x] MySQL connection configured (127.0.0.1:3306)
  - **Commit:** 4ebeb5e
  - Database: user_auth_db
  - Hibernate ORM configured with auto-update

### Security
- [x] Password encryption with BCrypt implemented
  - **Commit:** 4ebeb5e
  - Spring Security configuration in place
  - CORS enabled for http://localhost:3000

## IN-PROGRESS

### API Endpoints
- [ ] POST /api/auth/register - Implemented, needs testing
- [ ] POST /api/auth/login - Implemented, needs testing
- [ ] GET /api/user/me - Implemented, needs testing

### Frontend Styling
- [ ] Candyland theme CSS with pink, purple, blue color palette - COMPLETED
- [ ] Form animations and transitions - COMPLETED
- [ ] Responsive design - COMPLETED

### Testing & Integration
- [ ] End-to-end testing of registration flow
- [ ] End-to-end testing of login flow
- [ ] Dashboard functionality verification

## TODO

### Documentation
- [ ] Create ERD (Entity Relationship Diagram)
- [ ] Update UML diagrams from previous activity
- [ ] Take screenshots of Register page
- [ ] Take screenshots of Login page
- [ ] Take screenshots of Dashboard page
- [ ] Create FRS PDF with all diagrams and screenshots

### Repository Structure
- [ ] Organize /docs folder with FRS PDF
- [ ] Create README.md for the project
- [ ] Push all changes to public GitHub repository
- [ ] Create /mobile folder structure

### Deployment
- [ ] Verify backend runs on port 8081
- [ ] Verify frontend runs on port 3000
- [ ] Test full authentication flow
- [ ] Prepare for submission to MS Teams

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
- Styling: CSS with Candyland theme
- State Management: React Hooks (useState, useContext)
- HTTP Client: Fetch API
- Build Tool: Node.js/npm (react-scripts)

### Database Schema
`
users
 id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 firstName (VARCHAR, NOT NULL)
 lastName (VARCHAR, NOT NULL)
 email (VARCHAR, NOT NULL, UNIQUE)
 password (VARCHAR, NOT NULL, BCRYPT_ENCODED)
`

### API Endpoints Summary
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Register new user | No |
| POST | /api/auth/login | Login user | No |
| GET | /api/user/me | Get current user profile | Yes |

## Candyland Theme Colors
- Primary Pink: #FF69B4
- Secondary Purple: #9D4EDD
- Accent Blue: #3A86FF
- Highlight Yellow: #FFB703
- Accent Red: #FB5607
- Mint Green: #06D6A0
- Light Pink: #FFB3D9
- Cream: #FFF5E6

## Running the Application

### Start Backend
\\\ash
cd backend
.\mvnw spring-boot:run
# Runs on http://localhost:8081
\\\

### Start Frontend
\\\ash
cd web
npm install
npm start
# Runs on http://localhost:3000
\\\

### Database Setup
- Ensure MySQL is running on 127.0.0.1:3306
- Database will be auto-created by Hibernate
- Default credentials: root (no password)

## Notes for Next Session (Mobile)
- Mobile application to be implemented using React Native or Flutter
- Will use the same backend APIs
- Authentication token from login will be reused

---

**Last Updated:** February 9, 2026
**Status:** Lab 1 - Backend & Web Frontend Implementation
