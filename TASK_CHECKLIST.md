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
  - **Commit:** 630a0648
  - Persists `authToken` and `user` from payload

### Web Profile Layout (Feb 17, 2026)
- [x] Make Profile page non-scrollable (preserve layout)
  - **Commit:** 640ebfe5
- [x] Side-by-side sections: Personal vs Additional Information
  - **Commit:** e44c8557
- [x] Header bar: "My Profile" left, "Back to Dashboard" right; actions moved below Additional
  - **Commit:** 45a5c006

### Backend Profile Fields (Feb 17, 2026)
- [x] Add `gender` (String) and `age` (Integer) to `User` entity
  - **Commit:** 0d010ec5, 49a5f89a
- [x] Introduce `UpdateProfileRequest` DTO and persist `gender`/`age`
  - **Commit:** d2036d2b

### Mobile Profile Layout (Feb 17, 2026)
- [x] Fix `activity_profile.xml` to static card, header title + back, side-by-side input pairs
  - **Commit:** bb484a2c

### Documentation
- [x] FRS.md updated to a direct Google Docs link
  - **Commit:** 58ee47d7
- [x] Added Google Docs reference link in FRS (earlier)
  - **Commit:** 0f9d70c5

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
users table (current fields):
- id (BIGINT, PRIMARY, AUTO_INCREMENT)
- firstName, lastName, email (UNIQUE), password (BCRYPT)
- phone
- gender (STRING), age (INT)

Notes:
- address/city/country are deprecated in code paths; DB columns may exist if created earlier.
```

### API Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Register new user | No |
| POST | /api/auth/login | Login user | No |
| GET | /api/user/me | Get user profile | Yes (JWT) |
| PUT | /api/user/profile | Update user profile | Yes (JWT) |

## Current Theme: Beauty Palette (Playfair + Poppins)
- Headings Font: Playfair Display
- Body Font: Poppins
- Blush Pink: #F8C8DC
- Nude Beige: #F5E6DA
- Cream: #FFF8F0
- Soft Mauve: #C38EB4
- Light Gray: #EAEAEA

### Running the Application

**Start Backend** (port 8081):
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

**Database**: MySQL on 127.0.0.1:3306, database: `test`
---

**Last Updated:** February 17, 2026
**Status:** Lab 1 - Backend, Web & Mobile updates committed (small info)

## Daily Log

### February 17, 2026
- Web: Profile page set to non-scroll; sections aligned side-by-side; header/title and back positioned as requested.
- Backend: Added `gender` and `age` fields; introduced `UpdateProfileRequest` DTO; ensured persistence and retrieval via `/api/user/me` and `/api/user/profile`.
- Mobile: Fixed `activity_profile.xml` to static layout; added top header with title and back; side-by-side input pairs including Gender/Age.
- Theme: Applied Playfair Display (headings) and Poppins (body) with Beauty palette.
- Commits: All updates labeled "small info" and pushed.
