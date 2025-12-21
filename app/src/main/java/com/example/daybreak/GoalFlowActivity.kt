package com.example.daybreak

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.daybreak.UI.fragment.GoalFlowStep1Fragment

class GoalFlowActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_flow)

        // include 된 네비게이션 바 내부 View 잡기
        tvTitle = findViewById(R.id.tvTitle)
        ivBack = findViewById(R.id.ivBack)

        // 최초 진입 타이틀
        setTitleText("목표 카테고리 선택")

        // 최초 Fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.goalFlowContainer, GoalFlowStep1Fragment())
                .commit()
        }

        // 뒤로가기 버튼 클릭 처리
        ivBack.setOnClickListener {
            handleBack()
        }
    }

    /** 🔹 타이틀 변경용 함수 */
    fun setTitleText(title: String) {
        tvTitle.text = title
    }

    /** 🔹 뒤로가기 처리 */
    private fun handleBack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            // Step1에서 뒤로 → 그냥 종료 or 다이얼로그
            finish()
        }
    }
}
