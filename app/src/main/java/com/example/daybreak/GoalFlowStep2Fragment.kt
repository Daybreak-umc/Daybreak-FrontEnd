package com.example.daybreak

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.daybreak.databinding.FragmentGoalFlowStep2Binding

class GoalFlowStep2Fragment : Fragment() {

    private var _binding: FragmentGoalFlowStep2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalFlowStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitle()
        initEditText()
        initButtons()
    }

    /** 🔹 상단 네비게이션 타이틀 */
    override fun onResume() {
        super.onResume()
        (activity as? GoalFlowActivity)
            ?.setTitleText("미래의 내 모습 정의")
    }

    /** 🔹 본문 타이틀 강조 */
    private fun initTitle() {
        val fullText = "마음 카테고리 안에서\n미래의 내 모습을\n정의해주세요"
        val highlight = "미래의 내 모습"

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

    /** 🔹 입력창 + 글자 수 카운트 */
    private fun initEditText() {
        binding.etFutureMe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                val length = s?.length ?: 0
                binding.tvCount.text = "$length/80"

                // 한 글자 이상 입력 시 버튼 활성화
                binding.btnGoalCreate.btnPrimary.isEnabled = length > 0
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /** 🔹 버튼 초기화 */
    private fun initButtons() {
        // 랜덤 목표 추천 버튼
        binding.btnRandomGoal.btnPrimary.apply {
            text = "랜덤 목표 추천 받기"
            isEnabled = true
            setOnClickListener {
                // TODO: 랜덤 목표 추천 로직
            }
        }

        // 목표 설정하기 버튼
        binding.btnGoalCreate.btnPrimary.apply {
            text = "목표 설정하기"
            isEnabled = false
            setOnClickListener {
                submitGoal()
            }
        }
    }

    /** 🔹 목표 최종 생성 */
    private fun submitGoal() {
        val futureMeText = binding.etFutureMe.text.toString()

        // TODO: ViewModel or Activity로 데이터 전달
        // 예: (activity as GoalFlowActivity).setFutureMe(futureMeText)

        // 로딩 화면으로 이동
        val intent = Intent(requireContext(), GoalFlowLoadingActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}