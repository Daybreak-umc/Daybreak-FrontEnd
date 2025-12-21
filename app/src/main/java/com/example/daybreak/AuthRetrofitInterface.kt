package com.example.daybreak

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body login: Login): retrofit2.Response<LoginResponse>
}