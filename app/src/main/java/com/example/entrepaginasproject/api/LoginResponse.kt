package com.example.entrepaginasproject.api

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val usuario: UserData? // Puede ser nulo si falla el login
)

data class UserData(
    val id: Int,
    val nombre: String,
    val rol: String
)