package com.example.daybreak.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daybreak.Data.CheckItem
import com.example.daybreak.Data.WeeklyGoalData
import com.example.daybreak.GoalDetailFragment // ✅ [필수] 상세화면 import
import com.example.daybreak.R                 // ✅ [필수] 리소스 import
import com.example.daybreak.UI.adapter.WeeklyGoalRVAdapter
import com.example.daybreak.UI.main.MainActivity
import com.example.daybreak.databinding.FragmentWeeklyGoalsBinding

class WeeklyGoalsFragment : Fragment() {
    private var _binding: FragmentWeeklyGoalsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 2. 바인딩 초기화
        _binding = FragmentWeeklyGoalsBinding.inflate(inflater, container, false)

        // 3. MainActivity의 생성 상태(isGenerated) 연동
        val isGenerated = (activity as? MainActivity)?.isGenerated ?: false

        // 4. 상태에 따른 레이아웃(include된 뷰들) 가시성 제어
        if (isGenerated) {
            binding.layoutWeeklygoalsPre.root.visibility = View.GONE
            binding.layoutWeeklygoalsPost.root.visibility = View.VISIBLE

            //임시 코드(추후 수정 예정)

            // 더미 데이터 생성 (categoryId 1~6 활용)
            val dummyData = listOf(
                WeeklyGoalData(
                    1, "건강", "내가 S면 넌 나의 N이 되어줘...",
                    listOf(CheckItem(1L, "하루 30분 춤 연습"), CheckItem(2L, "물 2L 마시기"), CheckItem(3L,"영양제 먹기"))
                ),
                WeeklyGoalData(
                    2, "학업", "공부해서 남주자!",
                    listOf(CheckItem(4L, "코틀린 복습"), CheckItem(5L, "알고리즘 1문제"), CheckItem(6L, "영어 단어 외우기"))
                )
            )

            // 어댑터 연결 (post 레이아웃 내부의 리사이클러뷰 ID 확인)
            val adapter = WeeklyGoalRVAdapter(dummyData)
            binding.layoutWeeklygoalsPost.futuremeGoalitemRv.adapter = adapter

        } else {
            binding.layoutWeeklygoalsPre.root.visibility = View.VISIBLE
            binding.layoutWeeklygoalsPost.root.visibility = View.GONE

            // "미래의 나 생성하기" 버튼 클릭 시 이동 로직
            binding.layoutWeeklygoalsPre.weeklygoalsBtn.setOnClickListener {
                // 부모 액티비티인 MainActivity의 함수 호출
                (activity as? MainActivity)?.moveToGoalThisWeek()
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}