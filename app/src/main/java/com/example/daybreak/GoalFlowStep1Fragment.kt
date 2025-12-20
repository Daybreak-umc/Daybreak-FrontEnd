package com.example.daybreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daybreak.databinding.FragmentGoalFlowStep1Binding
import com.example.daybreak.databinding.ViewDropdownBinding
import com.example.daybreak.databinding.ViewButtonBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class GoalFlowStep1Fragment : Fragment() {

    private var _binding: FragmentGoalFlowStep1Binding? = null
    private val binding get() = _binding!!

    // 드롭다운 / 버튼 include binding
    private lateinit var dropdownBinding: ViewDropdownBinding
    private lateinit var buttonBinding: ViewButtonBinding

    private val categories = listOf(
        "건강", "마음", "학업", "커리어", "습관", "관계", "자기개발", "취미", "경제"
    )

    private var selectedCategory: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalFlowStep1Binding.inflate(inflater, container, false)

//        // include된 layout binding 연결
//        dropdownBinding = ViewDropdownBinding.bind(binding.categoryDropdown)
//        buttonBinding = ViewButtonBinding.bind(binding.btnGoalNext)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitle()
        initDropdown()
        initNextButton()
    }

    /** 상단 타이틀 세팅 */
    private fun initTitle() {
        binding.futureTitle.text = "목표 카테고리를 선택해 주세요"
    }

    /** 카테고리 드롭다운 */
    private fun initDropdown() {
        dropdownBinding.tvSelectedCategory.text = "카테고리 선택"

        dropdownBinding.root.setOnClickListener {
            showCategoryBottomSheet()
        }
    }

    /** 다음 버튼 */
    private fun initNextButton() {
        buttonBinding.btnPrimary.text = "다음"

        buttonBinding.btnPrimary.setOnClickListener {
            if (selectedCategory == null) {
                // 선택 안 했을 때는 그냥 return (토스트 넣고 싶으면 여기)
                return@setOnClickListener
            }

            // 다음 단계 이동 처리
            // findNavController().navigate(...)
        }
    }

    /** 카테고리 선택 BottomSheet */
    private fun showCategoryBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val sheetView = layoutInflater.inflate(R.layout.bottomsheet_goal_category, null)

        // TODO: RecyclerView 연결해서 categories 보여주기
        // 선택 시 selectedCategory 값 세팅

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}