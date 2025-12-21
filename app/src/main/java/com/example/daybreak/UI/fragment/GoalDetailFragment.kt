package com.example.daybreak

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.UI.adapter.GoalDetailAdapter

class GoalDetailFragment : Fragment(R.layout.fragment_goal_detail) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.goal_detail_Rv)

        // NestedScrollView 안에 있으므로 RecyclerView 자체 스크롤 비활성화
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = GoalDetailAdapter(
            items = createItems(),
            onDeleteClick = { showDeleteConfirmationDialog() }, // ✅ 클릭 시 다이얼로그 호출
            onNextClick = { /* 여기서는 사용 안 함 */ }
        )

        recyclerView.adapter = adapter
    }

    /**
     * 화면에 보여줄 데이터 리스트 생성
     */
    private fun createItems(): List<GoalDetailItem> {
        return listOf(
            GoalDetailItem.Header,

            // 일 년 (회색)
            GoalDetailItem.Period(
                periodText = "일 년",
                description = "운동이 일상의 일부가 됩니다.",
                type = PeriodType.YEAR
            ),

            // 여섯 달 (노랑)
            GoalDetailItem.Period(
                periodText = "여섯 달",
                description = "주 3-4회 운동 리듬이 잡히기 시작합니다.",
                type = PeriodType.SIX_MONTH
            ),

            // 세 달 (보라)
            GoalDetailItem.Period(
                periodText = "세 달",
                description = "운동 습관이 형성됩니다.",
                type = PeriodType.THREE_MONTH
            ),

            // 한 달 (주황)
            GoalDetailItem.Period(
                periodText = "한 달",
                description = "가볍게 시작해보세요.",
                type = PeriodType.MONTH
            ),

            // 일주일 (주황 재사용)
            GoalDetailItem.Period(
                periodText = "일주일",
                description = "작은 목표부터 달성해봐요.",
                type = PeriodType.WEEK
            ),

            // ✅ 목표 삭제하기 버튼 (리스트 맨 끝)
            GoalDetailItem.DeleteAction
        )
    }


    // 커스텀 다이얼로그 띄우기

    // 커스텀 다이얼로그 띄우기
    private fun showDeleteConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_goal_delete_pop_up_dialog)

        // 배경 투명하게
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.dialog_goal_delete_title_Tv)      // 제목 TextView ID
        val tvContent = dialog.findViewById<TextView>(R.id.dialog_delete_sub_Tv)    // 내용 TextView ID
        val btnCancel = dialog.findViewById<TextView>(R.id.dialog_delete_cancel_Bt) // 취소 버튼 (TextView or Button)
        val btnDelete = dialog.findViewById<TextView>(R.id.dialog_delete_run_Bt) // 삭제 버튼 (TextView or Button)

        // 2. 텍스트 내용 변경하기
        tvTitle.text = "목표를 삭제하시는 건가요?"
        tvContent.text = "목표를 삭제하면\n다시는 되돌릴 수 없어요!" // 줄바꿈(\n) 적용

        btnCancel.text = "취소"
        btnDelete.text = "삭제"


        // 3. 버튼 리스너 설정 (기존 로직 유지)
        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        btnDelete?.setOnClickListener {
            dialog.dismiss()
            performDeleteGoal()
        }

        dialog.show()
    }



    // 삭제 로직 (성공/실패 시뮬레이션)

    private fun performDeleteGoal() {
        // 실제 API 호출이 들어갈 자리
        // 지금은 무조건 성공한다고 가정 (실패 테스트를 하려면 false로 변경)
        val isSuccess = true

        if (isSuccess) {
            // ✅ 성공 시: 성공 토스트 띄우고 화면 종료
            showCustomToast(isSuccess = true, message = "목표가 삭제되었어요")
            parentFragmentManager.popBackStack()
        } else {
            // ❌ 실패 시: 실패 토스트 띄우고 화면 유지
            showCustomToast(isSuccess = false, message = "목표 삭제 중 오류가 발생했어요")
        }
    }


    // 커스텀 토스트 메시지 함수

    private fun showCustomToast(isSuccess: Boolean, message: String) {
        val inflater = LayoutInflater.from(requireContext())
        val layoutId = R.layout.fragment_toast_dialog
        val layout = inflater.inflate(layoutId, null)

        // 텍스트 설정
        val textView = layout.findViewById<TextView>(R.id.dialog_toast_tv)
        textView.text = message

        // 이미지 변경 로직 추가
        val imageView = layout.findViewById<android.widget.ImageView>(R.id.dialog_toast_iv)

        if (isSuccess) {
            // 성공 아이콘
            imageView.setImageResource(R.drawable.ic_progress_done_32) // ✅ 체크 아이콘 리소스명 확인 필요
        } else {
            // 실패 아이콘
            imageView.setImageResource(R.drawable.ic_progress_error_32) // ❌ 느낌표/에러 아이콘 리소스명 확인 필요
        }

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        // 화면 상단 중앙에 띄우기
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 40)
        // 가로 너비를 화면에 꽉 차게(혹은 여유있게) 조정하기

        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.9).toInt() // 화면 너비의 90%

        // 레이아웃의 최상위 뷰(보통 ConstraintLayout이나 LinearLayout)를 찾아서 너비 설정
        val rootLayout = layout.findViewById<View>(R.id.toast_container)

        // LayoutParams가 없는 경우 생성해줌
        if (rootLayout.layoutParams == null) {
            rootLayout.layoutParams = android.view.ViewGroup.LayoutParams(width, android.view.ViewGroup.LayoutParams.WRAP_CONTENT)
        } else {
            rootLayout.layoutParams.width = width
        }
        toast.show()

    }
}