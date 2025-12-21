package com.example.daybreak.Api

import com.example.daybreak.Data.Login
import com.example.daybreak.Data.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body login: Login): Response<LoginResponse>
}