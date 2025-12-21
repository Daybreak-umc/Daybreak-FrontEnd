package com.example.daybreak

import com.google.gson.annotations.SerializedName

data class Login(
    val email : String,
    val password : String
)
// 서버에서 받을 응답 데이터 클래스 (Swagger의 Response Schema 기준)
data class LoginResponse(
    @SerializedName("Success") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val result: LoginResult?
)

data class LoginResult(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("jwt_token") val accessToken: String
)
