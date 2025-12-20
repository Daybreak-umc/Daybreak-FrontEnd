package com.example.daybreak

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FuturemeFragment()   // '미래의 내 모습' 프래그먼트
            1 -> WeeklyGoalsFragment() // '이번 주 행동 목표' 프래그먼트
            else -> FuturemeFragment() // 기본값

        }
    }
}