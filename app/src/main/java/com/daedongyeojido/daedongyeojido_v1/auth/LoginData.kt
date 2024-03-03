package com.daedongyeojido.daedongyeojido_v1.auth

data class LoginRequest(
    val xquareId: String,
    val password: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiredAt: String,
    val refreshExpiredAt: String,
    val part: String
)