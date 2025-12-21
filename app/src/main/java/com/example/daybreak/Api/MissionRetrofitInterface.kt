package com.example.daybreak.Api

import com.example.daybreak.Data.MissionResponse
import retrofit2.Response
import retrofit2.http.PATCH
import retrofit2.http.Query

interface MissionRetrofitInterface {
    @PATCH("/api/v1/missions/complete")
    suspend fun completeMission(
        @Query("missionId") missionId: Long
    ): Response<MissionResponse>
}