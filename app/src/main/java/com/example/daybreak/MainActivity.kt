package com.example.daybreak

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.daybreak.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터 설정
        val adapter = MainVPAdapter(this)
        binding.homeViewpager.adapter = adapter

        // 탭 이름 배열
        val tabTitles = arrayOf("미래의 내 모습", "이번 주 행동 목표")

        // TabLayout과 ViewPager2를 연결
        TabLayoutMediator(binding.homeTablayout, binding.homeViewpager){tab, position->
            tab.text = tabTitles[position]}.attach()
        //상태바 확인을 위해 주석 처리
        //enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}