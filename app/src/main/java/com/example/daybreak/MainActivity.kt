package com.example.daybreak

import android.os.Bundle
// ❌ import android.os.Trace.isEnabled  <- 이 줄을 반드시 삭제하세요.
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.daybreak.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var isGenerated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 초기 UI 상태 설정 (함수로 분리해서 관리하면 편합니다)
        updateHeaderUI()

        // 2. 어댑터 및 탭 설정
        val adapter = MainVPAdapter(this)
        binding.homeViewpager.adapter = adapter
        val tabTitles = arrayOf("미래의 내 모습", "이번 주 행동 목표")
        TabLayoutMediator(binding.homeTablayout, binding.homeViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // 3. 뒤로가기 콜백 (최대한 유지)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()

                    // 스택이 비면 메인 UI 복구
                    supportFragmentManager.executePendingTransactions()
                    if (supportFragmentManager.backStackEntryCount == 0) {
                        binding.mainFrm.visibility = View.GONE
                        binding.homeTablayout.visibility = View.VISIBLE
                        binding.homeViewpager.visibility = View.VISIBLE
                        updateHeaderUI() // 헤더 다시 확인
                        binding.imgSymbolsDefault.visibility = View.VISIBLE
                    }
                } else {
                    this.isEnabled = false // ✅ 로컬 콜백의 isEnabled를 꺼야 함
                    onBackPressedDispatcher.onBackPressed()
                    this.isEnabled = true
                }
            }
        })
    }

    // 헤더 텍스트 가시성 제어 함수 (중복 코드 방지)
    private fun updateHeaderUI() {
        if(isGenerated){
            binding.layoutBeforeGenerate.visibility = View.GONE
            binding.layoutAfterGenerate.visibility = View.VISIBLE
            binding.goalCountTv.text = "3개"
        } else {
            binding.layoutBeforeGenerate.visibility = View.VISIBLE
            binding.layoutAfterGenerate.visibility = View.GONE
        }
    }

    fun moveToStep1() {
        // 이동할 때 전체 화면 컨테이너를 보이게 설정
        binding.mainFrm.visibility = View.VISIBLE

        binding.homeTablayout.visibility = View.GONE
        binding.homeViewpager.visibility = View.GONE
        binding.layoutBeforeGenerate.visibility = View.GONE
        binding.layoutAfterGenerate.visibility = View.GONE // 생성 후 헤더도 숨김
        binding.imgSymbolsDefault.visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, GoalFlowStep1Fragment())
            .addToBackStack(null)
            .commit()
    }
}