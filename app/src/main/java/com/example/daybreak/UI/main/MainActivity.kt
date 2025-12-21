package com.example.daybreak.UI.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.daybreak.GoalFlowActivity
import com.example.daybreak.GoalThisWeekFragment
import com.example.daybreak.R
import com.example.daybreak.ToastDialogFragment
import com.example.daybreak.UI.adapter.MainVPAdapter
import com.example.daybreak.UI.fragment.SettingFragment
import com.example.daybreak.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var isGenerated = false // 목표 생성 여부 플래그

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 1. 로그인 성공 토스트 메시지 확인

        val showToast = intent.getBooleanExtra("showLoginToast", false)
        if (showToast) {
            val toast = ToastDialogFragment("로그인이 완료되었어요", R.drawable.ic_progress_done_32)
            toast.show(supportFragmentManager, "LoginToast")
        }


        // 2. 어댑터 및 탭 레이아웃 설정
        val adapter = MainVPAdapter(this)
        binding.homeViewpager.adapter = adapter
        val tabTitles = arrayOf("미래의 내 모습", "이번 주 행동 목표")
        TabLayoutMediator(binding.homeTablayout, binding.homeViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()


        // 3. 초기 UI 설정

        updateHeaderVisibility()       // 헤더(날짜/멘트) 설정
        setToolbarMode(isMain = true)  // 툴바 메인 모드로 초기화


        // 4. 클릭 리스너 설정


        // (1) 설정 아이콘 클릭 -> 설정 프래그먼트로 이동
        binding.commonToolbar.layoutMainSetingIv.setOnClickListener {
            // SettingFragment로 이동하도록 설정 (테스트 필요 시 GoalDetailFragment로 변경 가능)
            openFragment(SettingFragment(), "설정")
        }

        // (2) 툴바 뒤로가기 버튼 클릭
        binding.commonToolbar.layoutFragBackIv.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 5. 뒤로가기 동작 제어 (Callback)

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                // UI 복구 로직은 아래 리스너에서 자동 처리됨
            } else {
                finish() // 백스택이 비어있으면 앱 종료
            }
        }

        // ---------------------------------------------------------------
        // 6. 백스택 변경 리스너 (UI 자동 복구)
        // ---------------------------------------------------------------
        supportFragmentManager.addOnBackStackChangedListener {
            // 백스택이 0이 되면 (= 메인 화면으로 돌아왔으면)
            if (supportFragmentManager.backStackEntryCount == 0) {
                restoreMainUI()
            }
        }
    }

    // [기능 함수 모음]


    /**
     * ✅ 1. 헤더(날짜, 멘트) 가시성 관리 함수
     */
    private fun updateHeaderVisibility() {
        if (isGenerated) {
            binding.layoutBeforeGenerate.visibility = View.GONE
            binding.layoutAfterGenerate.visibility = View.VISIBLE
            binding.goalCountTv.text = "3개"
        } else {
            binding.layoutBeforeGenerate.visibility = View.VISIBLE
            binding.layoutAfterGenerate.visibility = View.GONE
        }
    }

    /**
     * ✅ 2. 프래그먼트 열기 공통 함수
     * - 메인 UI 숨기기 + 툴바 디자인 변경 + 프래그먼트 표시
     */
    fun openFragment(fragment: androidx.fragment.app.Fragment, title: String) {
        // 1. 메인 UI 요소들 숨기기
        binding.homeTablayout.visibility = View.GONE
        binding.homeViewpager.visibility = View.GONE
        binding.layoutBeforeGenerate.visibility = View.GONE
        binding.layoutAfterGenerate.visibility = View.GONE
        binding.imgSymbolsDefault.visibility = View.GONE

        // 2. [디자인 핵심] 배너 배경(Gray_800) 숨기기
        // XML에 추가한 banner_background ID가 있어야 에러가 안 납니다.
        binding.bannerBackground.visibility = View.GONE

        // 3. 프래그먼트 컨테이너 보이기
        binding.mainFrm.visibility = View.VISIBLE

        // 4. 툴바 모드 변경 (타이틀 설정)
        setToolbarMode(isMain = false, title = title)

        // 5. 프래그먼트 교체 및 백스택 추가
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * ✅ 3. 메인 UI 복구 함수
     * - 프래그먼트가 닫히고 메인으로 돌아올 때 호출됨
     */
    private fun restoreMainUI() {
        binding.mainFrm.visibility = View.GONE // 프래그먼트 숨김

        // 메인 UI 요소들 다시 보이기
        binding.homeTablayout.visibility = View.VISIBLE
        binding.homeViewpager.visibility = View.VISIBLE
        binding.imgSymbolsDefault.visibility = View.VISIBLE

        // [디자인 핵심] 배너 배경 복구
        binding.bannerBackground.visibility = View.VISIBLE

        updateHeaderVisibility() // 헤더 상태 복구

        // 툴바 메인 모드로 복구 (로고 보임)
        setToolbarMode(isMain = true)
    }

    /**
     * ✅ 4. 툴바 상태 변경 함수
     */
    fun setToolbarMode(isMain: Boolean, title: String = "") {
        binding.commonToolbar.apply {
            if (isMain) {
                layoutMainCommonLogoIv.visibility = View.VISIBLE
                layoutMainSetingIv.visibility = View.VISIBLE
                layoutFragBackIv.visibility = View.GONE
                layoutFragTitleTv.visibility = View.GONE
            } else {
                layoutMainCommonLogoIv.visibility = View.GONE
                layoutMainSetingIv.visibility = View.GONE
                layoutFragBackIv.visibility = View.VISIBLE
                layoutFragTitleTv.visibility = View.VISIBLE
                layoutFragTitleTv.text = title
            }
        }
    }

    /**
     * ✅ 5. 외부 호출용 (GoalFlow 등에서 사용)
     */
    fun moveToStep1() {
        startActivity(Intent(this, GoalFlowActivity::class.java))
    }

    fun moveToGoalThisWeek() {
        openFragment(GoalThisWeekFragment(), "이번 주 행동 목표")
    }
}