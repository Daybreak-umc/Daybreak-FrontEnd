package com.example.daybreak.Data

data class MissionResponse(
    val success : Boolean,
    val code : String,
    val message:String,
    val data:MissionData
)

data class MissionData(
    val missionId:Long,
    val update:String
)