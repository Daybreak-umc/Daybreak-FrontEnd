package com.example.daybreak.UI.`object`

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.daybreak.R

object CategoryMapper {
    // 카테고리 ID 값에 따른 색상 리소스 ID 반환
    fun getColorResId(categoryId:Int):Int {
       return when(categoryId){
           1-> R.color.colorHealth
           7 -> R.color.colorMind
           8-> R.color.colorStudy
           9-> R.color.colorCareer
           10-> R.color.colorHabit
           11-> R.color.colorRelationship
           12-> R.color.colorGrowth
           13-> R.color.colorHobby
           14-> R.color.colorFinance
           else -> R.color.white
       }
    }

    fun getColor(context: Context, categoryId:Int):Int{
        return ContextCompat.getColor(context, getColorResId(categoryId))
    }
}