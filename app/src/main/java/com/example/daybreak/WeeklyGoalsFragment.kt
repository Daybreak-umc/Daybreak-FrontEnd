package com.example.daybreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class WeeklyGoalsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 일단은 기본 레이아웃을 리턴하게만 설정
        return inflater.inflate(R.layout.fragment_weekly_goals, container, false)
    }
}