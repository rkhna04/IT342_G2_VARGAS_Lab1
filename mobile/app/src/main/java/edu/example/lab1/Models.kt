package edu.example.lab1

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val createdAt: String? = null
)

data class AuthResponse(
    val status: String,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    val user: User,
    val token: String
)

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T?
)

data class UserResponse(
    val status: String,
    val message: String,
    val data: User?
)
