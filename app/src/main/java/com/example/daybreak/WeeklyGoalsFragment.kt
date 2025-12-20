package com.example.daybreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

            // 여기서 체크박스 리스트 제어
            
        } else {
            binding.layoutWeeklygoalsPre.root.visibility = View.VISIBLE
            binding.layoutWeeklygoalsPost.root.visibility = View.GONE
            
        }
        return binding.root
    }

}