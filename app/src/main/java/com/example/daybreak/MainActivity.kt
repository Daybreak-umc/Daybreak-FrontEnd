package com.example.daybreak

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.example.daybreak.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var isGenerated = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터 및 탭 설정
        val adapter = MainVPAdapter(this)
        binding.homeViewpager.adapter = adapter
        val tabTitles = arrayOf("미래의 내 모습", "이번 주 행동 목표")
        TabLayoutMediator(binding.homeTablayout, binding.homeViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // 초기 헤더 설정
        updateHeaderVisibility()

        // 1. 설정 아이콘 클릭 시
        binding.commonToolbar.layoutMainSetingIv.setOnClickListener {
            //openFragment(SettingFragment(), "설정") // 임시로 프래그러먼트 전환 변경 실시
            openFragment(GoalDetailFragment(), "목표 상세")
        }

        // 2. 뒤로가기 버튼 클릭 시
        binding.commonToolbar.layoutFragBackIv.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 뒤로가기 콜백
        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()

                // 백스택이 0이 되면(=메인으로 돌아오면) 메인 UI 복구
                if (supportFragmentManager.backStackEntryCount == 1) { // pop하기 전이 1개였으니 이제 0개
                    restoreMainUI()
                }
            } else {
                finish()
            }
        }

        // 백스택 변경 리스너 추가 (프래그먼트가 pop 될 때 UI 자동 갱신을 위해)
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                restoreMainUI()
            }
        }
    }

    // ✅ 헤더 가시성 관리 함수
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

    // ✅ 프래그먼트 열기 공통 함수
    private fun openFragment(fragment: androidx.fragment.app.Fragment, title: String) {
        // 1. 메인 UI 숨기기
        binding.homeTablayout.visibility = View.GONE
        binding.homeViewpager.visibility = View.GONE
        binding.layoutBeforeGenerate.visibility = View.GONE
        binding.layoutAfterGenerate.visibility = View.GONE
        binding.imgSymbolsDefault.visibility = View.GONE

        // 2. 프래그먼트 컨테이너 보이기
        binding.mainFrm.visibility = View.VISIBLE

        // 3. 툴바 모드 변경 (상세 페이지 모드)
        setToolbarMode(isMain = false, title = title)

        // 4. 프래그먼트 교체
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .addToBackStack(null)
            .commit()
    }

    // ✅ 메인 UI 복구 함수
    private fun restoreMainUI() {
        binding.mainFrm.visibility = View.GONE // 프래그먼트 숨김

        binding.homeTablayout.visibility = View.VISIBLE
        binding.homeViewpager.visibility = View.VISIBLE
        binding.imgSymbolsDefault.visibility = View.VISIBLE

        updateHeaderVisibility() // 헤더 상태 복구

        // 툴바 메인 모드로 복구
        setToolbarMode(isMain = true)
    }

    // ✅ 툴바 상태 변경 함수 (이게 핵심!)
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

    // 외부에서 호출할 함수 (GoalFlow 등에서 사용)
    fun moveToStep1() {
        openFragment(GoalFlowStep1Fragment(), "목표 생성") // 타이틀은 원하는 대로 변경
    }
}
