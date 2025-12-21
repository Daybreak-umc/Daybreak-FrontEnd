package com.example.daybreak


data class WeeklyGoalData(
    val categoryId: Int,
    val typeName: String, // "건강", "학업" 등
    val message: String,
    val checkList: List<CheckItem>
)

data class CheckItem(
    val text: String,
    var isChecked: Boolean = false
)