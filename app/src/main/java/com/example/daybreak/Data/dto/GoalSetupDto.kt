package com.example.daybreak.Data.dto

data class SetupRequest(
    val category: String,
    val futureMe: String
)

data class SetupResponse(
    val success: Boolean,
    val message: String? = null
)