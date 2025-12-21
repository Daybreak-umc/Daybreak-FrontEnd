package com.example.daybreak

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.adapter.GoalThisWeekAdapter

class GoalThisWeekFragment : Fragment(R.layout.fragment_goal_this_week) {

    private lateinit var rvGoalThisWeek: RecyclerView
    private lateinit var adapter: GoalThisWeekAdapter

    // 버튼 변수
    private lateinit var btnRetry: TextView
    private lateinit var btnConfirm: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupButtons(view)
    }

    private fun setupRecyclerView(view: View) {
        // ⚠️ XML의 RecyclerView ID 확인
        rvGoalThisWeek = view.findViewById(R.id.layout_goal_this_week_Rv)

        rvGoalThisWeek.layoutManager = LinearLayoutManager(requireContext())

        // 어댑터 초기화 및 연결
        adapter = GoalThisWeekAdapter()
        rvGoalThisWeek.adapter = adapter

        // 테스트 데이터 생성
        val dummyData = listOf(
            GoalItemData("주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4"),
            GoalItemData("주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4"),
            GoalItemData("주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4"),
            GoalItemData("주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4"),
            GoalItemData("주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4회 운동 리듬이 잡히기 시작합니다. 주 3-4")
        )

        // 데이터 적용
        adapter.updateData(dummyData)
    }

    private fun setupButtons(view: View) {
        // ⚠️ XML의 버튼 ID 확인 (없으면 앱 죽음)
        // 만약 XML에 ID가 다르게 되어있다면 그 이름으로 바꿔주세요.
        btnRetry = view.findViewById(R.id.layout_goal_this_week_recommend_Tv)
        btnConfirm = view.findViewById(R.id.layout_goal_this_week_run_Tv) // 아까 수정한 ID

        // 만약 ID를 못 찾으면 null 에러가 날 수 있으니 XML을 꼭 확인해주세요!

        btnRetry.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnConfirm.setOnClickListener {
            Toast.makeText(context, "목표가 설정되었습니다!", Toast.LENGTH_SHORT).show()
        }
    }
}
