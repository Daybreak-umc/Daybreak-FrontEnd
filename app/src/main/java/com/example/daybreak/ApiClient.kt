package com.example.daybreak

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val BASE_URL = "http://15.165.14.192:8080/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService: AuthRetrofitInterface by lazy {
        retrofit.create(AuthRetrofitInterface::class.java)
    }
    //val authService: AuthRetrofitInterface = retrofit.create(AuthRetrofitInterface::class.java)
}