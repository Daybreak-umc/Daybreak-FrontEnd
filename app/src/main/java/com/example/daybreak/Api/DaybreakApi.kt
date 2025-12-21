package com.example.daybreak.Api

import com.example.daybreak.Data.dto.CreateMissionsResponse
import com.example.daybreak.Data.dto.RandomGoalResponse
import com.example.daybreak.Data.dto.SetupRequest
import com.example.daybreak.Data.dto.SetupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DaybreakApi {

    @POST("/api/v1/users/setup")
    suspend fun setupUser(@Body body: SetupRequest): Response<SetupResponse>

    @POST("/api/v1/users/random")
    suspend fun getRandomGoal(): Response<RandomGoalResponse>

    @POST("/api/v1/missions")
    suspend fun createWeeklyMissions(): Response<CreateMissionsResponse>
}
