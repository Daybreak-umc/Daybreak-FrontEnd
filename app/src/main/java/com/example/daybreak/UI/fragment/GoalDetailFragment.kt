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
import com.example.daybreak.Data.GoalDetailItem
import com.example.daybreak.Data.PeriodType
import com.example.daybreak.UI.adapter.GoalDetailAdapter
import com.example.daybreak.UI.main.MainActivity


class GoalDetailFragment : Fragment(R.layout.fragment_goal_detail) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalDetailAdapter

    // ✅ 모드 구분 변수 (기본값은 삭제 모드)
    private var viewMode: String = "VIEW"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 전달받은 모드 확인
        viewMode = arguments?.getString("VIEW_MODE") ?: "VIEW"

        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.goal_detail_Rv)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 2. 어댑터 설정 (클릭 리스너 분기 처리)
        adapter = GoalDetailAdapter(
            items = createItems(),
            onDeleteClick = {
                if (viewMode == "VIEW") showDeleteConfirmationDialog()
            },
            onNextClick = {
                if (viewMode == "CREATE") handleNextClick()
            }
        )

        recyclerView.adapter = adapter
    }

    private fun createItems(): List<GoalDetailItem> {
        val list = mutableListOf<GoalDetailItem>()

        // 공통 아이템 추가
        list.add(GoalDetailItem.Header)
        list.add(GoalDetailItem.Period("일 년", "운동이 일상의 일부가 됩니다.", PeriodType.YEAR))
        list.add(GoalDetailItem.Period("여섯 달", "주 3-4회 운동 리듬이 잡히기 시작합니다.", PeriodType.SIX_MONTH))
        list.add(GoalDetailItem.Period("세 달", "운동 습관이 형성됩니다.", PeriodType.THREE_MONTH))
        list.add(GoalDetailItem.Period("한 달", "가볍게 시작해보세요.", PeriodType.MONTH))
        list.add(GoalDetailItem.Period("일주일", "작은 목표부터 달성해봐요.", PeriodType.WEEK))

        // ✅ 모드에 따라 마지막 버튼 결정
        if (viewMode == "CREATE") {
            list.add(GoalDetailItem.NextAction) // "이대로 진행하기" 버튼
        } else {
            list.add(GoalDetailItem.DeleteAction) // "삭제하기" 버튼
        }

        return list
    }

    // [최초 생성 모드] 다음 단계 진행
    private fun handleNextClick() {
        (activity as? MainActivity)?.openFragment(GoalThisWeekFragment(), "목표 완료")
    }


    // [메인에서 삭제 모드] 삭제 다이얼로그
    private fun showDeleteConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_goal_delete_pop_up_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.dialog_goal_delete_title_Tv)
        val tvContent = dialog.findViewById<TextView>(R.id.dialog_delete_sub_Tv)
        val btnCancel = dialog.findViewById<TextView>(R.id.dialog_delete_cancel_Bt)
        val btnDelete = dialog.findViewById<TextView>(R.id.dialog_delete_run_Bt)

        tvTitle.text = "목표를 삭제하시는 건가요?"
        tvContent.text = "목표를 삭제하면\n다시는 되돌릴 수 없어요!"
        btnCancel.text = "취소"
        btnDelete.text = "삭제"

        btnCancel?.setOnClickListener { dialog.dismiss() }
        btnDelete?.setOnClickListener {
            dialog.dismiss()
            performDeleteGoal()
        }
        dialog.show()
    }

    // ✅ [수정] 삭제 동작 수행 함수
    private fun performDeleteGoal() {
        // 나중에 여기서 API 호출 (DELETE /goals/{goalId})

        val isSuccess = true

        if (isSuccess) {
            // 1. 성공 토스트 띄우기
            showCustomToast(true, "목표가 삭제되었어요")

            // 2. [핵심] 화면 뒤로가기 (MainActivity 리스너가 감지해서 UI 복구함)
            // parentFragmentManager.popBackStack() 은 뷰페이저 내부가 아니라
            // MainActivity의 FragmentManager에서 빼야 하므로 activity?.supportFragmentManager를 사용 권장

            activity?.supportFragmentManager?.popBackStack()

        } else {
            showCustomToast(false, "목표 삭제 중 오류가 발생했어요")
        }
    }

    // 커스텀 토스트
    private fun showCustomToast(isSuccess: Boolean, message: String) {
        val inflater = LayoutInflater.from(requireContext())
        val layoutId = R.layout.fragment_toast_dialog
        val layout = inflater.inflate(layoutId, null)

        val textView = layout.findViewById<TextView>(R.id.dialog_toast_tv)
        textView.text = message

        val imageView = layout.findViewById<android.widget.ImageView>(R.id.dialog_toast_iv)
        if (isSuccess) imageView.setImageResource(R.drawable.ic_progress_done_32)
        else imageView.setImageResource(R.drawable.ic_progress_error_32)

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 40)

        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.9).toInt()

        val rootLayout = layout.findViewById<View>(R.id.toast_container)
        if (rootLayout.layoutParams == null) {
            rootLayout.layoutParams = android.view.ViewGroup.LayoutParams(width, android.view.ViewGroup.LayoutParams.WRAP_CONTENT)
        } else {
            rootLayout.layoutParams.width = width
        }
        toast.show()
    }

    companion object {
        fun newInstance(mode: String): GoalDetailFragment {
            val fragment = GoalDetailFragment()
            val args = Bundle()
            args.putString("VIEW_MODE", mode)
            fragment.arguments = args
            return fragment
        }
    }
}
