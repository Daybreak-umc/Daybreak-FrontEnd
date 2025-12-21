package com.example.daybreak.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daybreak.databinding.FragmentGoalFlowStep1Binding

class GoalFlowStep1Fragment : Fragment() {

    // 1. 바인딩 객체 선언 (메모리 누수 방지 패턴)
    private var _binding: FragmentGoalFlowStep1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 2. 바인딩 초기화
        _binding = FragmentGoalFlowStep1Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3. 여기서 버튼 클릭 이벤트나 UI 초기화 로직을 작성합니다.
        // 예: 다음 단계 버튼 클릭 시
        // binding.btnNext.setOnClickListener { ... }

        // 2. 뒤로 가기 버튼 활성화
        binding.goalFlowTitle.ivBack.setOnClickListener {
            // 액티비티의 onBackPressed를 호출하여 MainActivity에 설정한 로직이 실행되게 함
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    // 4. 프래그먼트 파괴 시 바인딩 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}