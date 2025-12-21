package com.example.daybreak

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
            // Step1에서 뒤로가기 → 다이얼로그 표시
            showExitDialog()
        }
    }

    private fun showExitDialog() {
        val dialog = android.app.Dialog(this)
        dialog.setContentView(R.layout.dialog_exit_goal_flow)
        dialog.setCancelable(false)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


        // 제목 / 설명
        val tvTitle1 = dialog.findViewById<TextView>(R.id.dialog_title1)
        val tvTitle2 = dialog.findViewById<TextView>(R.id.dialog_title2)

        tvTitle1.text = "목표 설정을 포기하시나요?"
        tvTitle2.text = "조금만 더 하면\n새로운 미래의 내가 보여요!"

        // 🔥 include된 버튼은 바로 Button
        val btnContinue = dialog.findViewById<android.widget.Button>(R.id.dialog_button1)
        val btnExit = dialog.findViewById<android.widget.Button>(R.id.dialog_button2)

        btnContinue.text = "계속 진행"
        btnExit.text = "포기"

        btnContinue.setOnClickListener {
            dialog.dismiss()
        }

        btnExit.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


}
