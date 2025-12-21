package com.example.daybreak.UI.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.GoalCategoryAdapter
import com.example.daybreak.GoalFlowActivity
import com.example.daybreak.R
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
        forcePrimaryButtonColor()
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

        nextButton.setOnClickListener {
            goNext()
        }
    }


    /** 🔹 카테고리 드롭다운 */
    private fun initDropdown() {
        binding.categoryDropdown.root.setOnClickListener {
            showCategoryDropdown(it)
        }

    }

    /** 🔹 카테고리 선택 BottomSheet */
    private var selectedCategory: String? = null
    private var categoryPopup: PopupWindow? = null


    private fun showCategoryDropdown(anchor: View) {
        // 이미 열려 있으면 닫기 (토글)
        if (categoryPopup?.isShowing == true) {
            categoryPopup?.dismiss()
            return
        }

        val popupView = layoutInflater.inflate(R.layout.bottomsheet_goal_category, null)
        val rv = popupView.findViewById<RecyclerView>(R.id.rvCategory)

        rv.layoutManager = LinearLayoutManager(requireContext())

        val adapter = GoalCategoryAdapter(categories) { selected ->
            // ✅ 선택 상태만 변경
            selectedCategory = selected
            binding.categoryDropdown.tvSelectedCategory.text = selected
        }

        // 기존 선택 유지
        adapter.setSelected(selectedCategory)

        rv.adapter = adapter

        categoryPopup = PopupWindow(
            popupView,
            anchor.width,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            elevation = 8f

            val gap = resources.getDimensionPixelSize(R.dimen.dropdown_gap)
            showAsDropDown(anchor, 0, gap)


            setOnDismissListener {
                binding.categoryDropdown.ivArrow.animate()
                    .rotation(0f).setDuration(150).start()
            }
        }

        // 화살표 회전
        binding.categoryDropdown.ivArrow.animate()
            .rotation(180f).setDuration(150).start()
    }

    private fun forcePrimaryButtonColor() {
        val btn = binding.btnGoalNext.btnPrimary

        // 🔥 이게 핵심
        btn.backgroundTintList =
            ColorStateList.valueOf(requireContext().getColor(R.color.Primary_500))

        btn.setTextColor(requireContext().getColor(R.color.Gray_0))
    }


    /** 🔹 Step2 이동 */
    private fun goNext() {
        val f = GoalFlowStep2Fragment().apply {
            arguments = Bundle().apply {
                putString("category", selectedCategory ?: "마음") // 선택값
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.goalFlowContainer, f)
            .addToBackStack(null)
            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
