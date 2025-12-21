package com.example.daybreak.Api

import com.example.daybreak.Data.Login
import com.example.daybreak.Data.LoginResponse
import retrofit2.Response

class AuthRepository(private val authService: AuthRetrofitInterface) {
    suspend fun login(loginRequest: Login): Response<LoginResponse> {
        return authService.login(loginRequest)
    }
}