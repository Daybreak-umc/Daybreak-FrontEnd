package com.example.daybreak

import retrofit2.Response

class AuthRepository(private val authService: AuthRetrofitInterface) {
    suspend fun login(loginRequest: Login): retrofit2.Response<LoginResponse> {
        return authService.login(loginRequest)
    }
}