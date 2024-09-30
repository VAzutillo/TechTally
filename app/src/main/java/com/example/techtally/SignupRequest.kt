package com.example.techtally

// Data class representing the signup request body
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)