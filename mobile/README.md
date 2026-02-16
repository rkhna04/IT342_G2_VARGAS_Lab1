# Lab1 Mobile - Android Kotlin Application

User Registration and Authentication system for Android, connecting to Spring Boot backend.

## Features

- **User Registration** - Create new account with first name, last name, email, and password
- **User Login** - Authenticate with email and password
- **Dashboard** - Home screen after successful login
- **Profile Management** - View and edit user profile (all fields)
- **Logout** - Logout with confirmation dialog
- **JWT Authentication** - Secure token-based authentication
- **Protected Routes** - Dashboard and Profile screens require authentication
- **Material Design** - Modern Android UI with custom brown theme

## Architecture

### Kotlin Classes

- **Models.kt** - Data classes for User, AuthResponse, APIResponse
- **ApiClient.kt** - Retrofit setup with API interface and Kotlin Coroutines
- **AuthManager.kt** - SharedPreferences wrapper for token and user management
- **AuthActivities.kt** - Main, Login, and Register screens
- **ProtectedActivities.kt** - Dashboard and Profile screens (requires authentication)

### Layouts

- **activity_main.xml** - Entry point with Login/Register buttons
- **activity_login.xml** - Login form (email, password)
- **activity_register.xml** - Registration form (first name, last name, email, password)
- **activity_dashboard.xml** - Welcome screen with Profile and Logout buttons
- **activity_profile.xml** - Editable profile with all user fields

### Resources

- **strings.xml** - String resources
- **colors.xml** - Color palette (Brown theme)
- **themes.xml** - Material Design theme styling

## Backend Integration

**Base URL:** `http://10.0.2.2:8081/api/` (Android emulator special IP)

### API Endpoints

- `POST /auth/register` - Register new user
- `POST /auth/login` - Login user
- `POST /auth/logout` - Logout user
- `GET /user/me` - Get profile (requires Authorization header)
- `PUT /user/profile` - Update profile (requires Authorization header)

## Development Setup

### Requirements

- Android Studio 2022.3 or higher
- Java Development Kit (JDK) 11+
- Android SDK (API level 26+)
- Gradle 8.0

### Dependencies

- Retrofit - HTTP client
- Gson - JSON serialization
- Coroutines - Async operations
- AndroidX libraries
- Material Design 3

### Build & Run

1. Clone the repository
2. Open in Android Studio
3. Click "Build" → "Make Project"
4. Select an emulator or connect a physical device
5. Click "Run" → "Run 'app'"

## Theme Colors

- **Primary:** #8B6F47 (Warm Brown)
- **Primary Dark:** #5D4E37 (Dark Brown)
- **Accent:** #C17855 (Terracotta)
- **Accent Light:** #D4A574 (Tan)
- **Background:** #FAF8F5 (Warm Cream)
- **Error:** #B85450 (Muted Red)

## Notes

- Token is stored in SharedPreferences with "Bearer " prefix
- JWT validation handled by backend
- All API calls use Kotlin Coroutines for non-blocking operations
- Progress bars shown during API calls
- Comprehensive error handling with user-friendly messages
