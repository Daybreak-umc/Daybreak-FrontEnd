package com.example.daybreak

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.databinding.FragmentGoalFlowStep1Binding
import com.google.android.material.bottomsheet.BottomSheetDialog

class GoalFlowStep1Fragment : Fragment() {

    private var _binding: FragmentGoalFlowStep1Binding? = null
    private val binding get() = _binding!!

    private val categories =
        listOf("건강", "마음", "학업", "커리어", "습관", "관계", "자기개발", "취미", "경제")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalFlowStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitle()
        initButtons()
        initDropdown()
    }

    /** 🔹 상단 네비게이션 타이틀 설정 (Activity에 요청) */
    override fun onResume() {
        super.onResume()
        (activity as? GoalFlowActivity)
            ?.setTitleText("목표 카테고리 설정")
    }


    /** 🔹 본문 제목 강조 */
    private fun initTitle() {
        val fullText = "목표 카테고리를\n선택해주세요"
        val highlight = "목표 카테고리"

        val spannable = SpannableString(fullText)
        val start = fullText.indexOf(highlight)
        val end = start + highlight.length

        spannable.setSpan(
            ForegroundColorSpan(requireContext().getColor(R.color.Primary_500)),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.futureTitle.text = spannable
    }

    /** 🔹 다음 버튼 */
    private fun initButtons() {
        val nextButton = binding.btnGoalNext.btnPrimary

        nextButton.text = "다음으로"
        nextButton.isEnabled = false

        nextButton.setOnClickListener {
            goNext()
        }
    }


    /** 🔹 카테고리 드롭다운 */
    private fun initDropdown() {
        binding.categoryDropdown.root.setOnClickListener {
            showCategoryBottomSheet()
        }
    }

    /** 🔹 카테고리 선택 BottomSheet */
    private fun showCategoryBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottomsheet_goal_category, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvCategory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = GoalCategoryAdapter(categories) { selected ->
            binding.categoryDropdown.tvSelectedCategory.text = selected
            binding.btnGoalNext.btnPrimary.isEnabled = true
            dialog.dismiss()
        }

        recyclerView.adapter = adapter
        dialog.setContentView(view)
        dialog.show()
    }


    /** 🔹 Step2 이동 */
    private fun goNext() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.goalFlowContainer, GoalFlowStep2Fragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
