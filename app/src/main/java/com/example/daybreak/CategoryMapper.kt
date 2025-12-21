package com.example.daybreak

import android.content.Context
import androidx.core.content.ContextCompat

object CategoryMapper {
    // 카테고리 ID 값에 따른 색상 리소스 ID 반환
    fun getColorResId(categoryId:Int):Int {
       return when(categoryId){
           1->R.color.colorHealth
           2 -> R.color.colorMind
           3->R.color.colorStudy
           4->R.color.colorCareer
           5->R.color.colorHabit
           6->R.color.colorRelationship
           7->R.color.colorGrowth
           8->R.color.colorHobby
           9->R.color.colorFinance
           else -> R.color.white
       }
    }

    fun getColor(context: Context, categoryId:Int):Int{
        return ContextCompat.getColor(context, getColorResId(categoryId))
    }
}