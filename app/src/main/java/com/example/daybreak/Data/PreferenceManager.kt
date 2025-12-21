package com.example.daybreak.Data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)

    // 토큰 저장
    fun saveAccessToken(token: String) {
        prefs.edit().putString("ACCESS_TOKEN", token).apply()
    }

    // 토큰 불러오기
    fun getAccessToken(): String? {
        return prefs.getString("ACCESS_TOKEN", null)
    }

    // 로그아웃 시 토큰 삭제
    fun clearToken() {
        prefs.edit().remove("ACCESS_TOKEN").apply()
    }
}