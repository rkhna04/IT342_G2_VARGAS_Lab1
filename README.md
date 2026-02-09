# IT342_G2_VARGAS_Lab1 - User Registration and Authentication System

A complete User Registration and Authentication system with Spring Boot backend and React frontend.

## 🎯 Features

- User registration and login with JWT authentication
- Profile management with editable fields (name, email, phone, address, city, country)
- Custom logout confirmation modal with Yes/No buttons
- Minimalist Brown professional theme
- BCrypt password encryption
- Protected routes and API endpoints

## 📁 Project Structure

```
IT342_G2_VARGAS_Lab1/
├── backend/          # Spring Boot REST API
├── web/              # React frontend application
├── mobile/           # Mobile app (Lab 2)
├── docs/             # Documentation and FRS PDF
├── README.md
└── TASK_CHECKLIST.md
```

## 🚀 Quick Start

### Prerequisites
- Java 11+
- Node.js 16+ & npm
- MySQL 8.0+

### Run Backend
```bash
cd backend
mvn spring-boot:run
```
Backend: **http://localhost:8081**

### Run Frontend
```bash
cd web
npm install
npm start
```
Frontend: **http://localhost:3000**

### Database
- MySQL: `127.0.0.1:3306`
- Database: `lab1_db` (auto-created by Hibernate)
- Update credentials in [backend/application.properties](backend/application.properties)

## 📡 API Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login user | No |
| GET | `/api/user/me` | Get user profile | JWT |
| PUT | `/api/user/me` | Update user profile | JWT |

### Example: Register
```json
POST /api/auth/register
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

## 🎨 Tech Stack

**Backend:** Spring Boot 2.7.12, Java 11, MySQL 8.0, Hibernate, JWT, BCrypt  
**Frontend:** React 18.2, Custom CSS (Minimalist Brown theme)

## 📄 License

This project is for educational purposes - IT342 Lab 1

---
**Last Updated:** February 9, 2026
