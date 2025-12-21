package com.example.daybreak

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.daybreak.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var isGenerated = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //어댑터 및 탭 설정
        val adapter = MainVPAdapter(this)
        binding.homeViewpager.adapter = adapter
        val tabTitles = arrayOf("미래의 내 모습", "이번 주 행동 목표")
        TabLayoutMediator(binding.homeTablayout, binding.homeViewpager){tab, position->
            tab.text = tabTitles[position]}.attach()

        //헤더 변환 로직
        if(isGenerated){
            binding.layoutBeforeGenerate.visibility = View.GONE
            binding.layoutAfterGenerate.visibility = View.VISIBLE
            binding.goalCountTv.text = "3개"
        } else{
            binding.layoutBeforeGenerate.visibility = View.VISIBLE
            binding.layoutAfterGenerate.visibility = View.GONE
        }

        // 1. 설정 아이콘 클릭 시
        binding.commonToolbar.layoutMainSetingIv.setOnClickListener {
            val settingFragment = SettingFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fl, settingFragment) // 컨테이너 ID 주의
                .addToBackStack(null)
                .commit()

            // 툴바 UI 변경
            binding.commonToolbar.apply {
                layoutMainCommonLogoIv.visibility = View.GONE
                layoutMainSetingIv.visibility = View.GONE
                layoutFragBackIv.visibility = View.VISIBLE
                layoutFragTitleTv.visibility = View.VISIBLE
                layoutFragTitleTv.text = "설정"
            }
        }

        // 2. 뒤로가기 버튼 클릭 시
        binding.commonToolbar.layoutFragBackIv.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()

                // 1. 메인 툴바 UI 복구
                binding.commonToolbar.apply {
                    layoutMainCommonLogoIv.visibility = View.VISIBLE
                    layoutMainSetingIv.visibility = View.VISIBLE
                    layoutFragBackIv.visibility = View.GONE
                    layoutFragTitleTv.visibility = View.GONE
                }

                // 2.moveToStep1에서 GONE 시켰던 뷰들을 다시 보이게 복구
                binding.apply {
                    homeTablayout.visibility = View.VISIBLE
                    homeViewpager.visibility = View.VISIBLE
                    imgSymbolsDefault.visibility = View.VISIBLE
                    mainFrm.visibility = View.GONE // 프래그먼트 컨테이너는 다시 숨김

                    // 헤더 상태 복구
                    if(isGenerated){
                        layoutBeforeGenerate.visibility = View.GONE
                        layoutAfterGenerate.visibility = View.VISIBLE
                    } else {
                        layoutBeforeGenerate.visibility = View.VISIBLE
                        layoutAfterGenerate.visibility = View.GONE
                    }
                }
            } else {
                finish()
            }
        }

    }

    fun moveToStep1() {
        binding.mainFrm.visibility = View.VISIBLE
        binding.homeTablayout.visibility = View.GONE
        binding.homeViewpager.visibility = View.GONE
        binding.layoutBeforeGenerate.visibility = View.GONE
        binding.layoutAfterGenerate.visibility = View.GONE
        binding.imgSymbolsDefault.visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, GoalFlowStep1Fragment())
            .addToBackStack(null)
            .commit()
    }
}
