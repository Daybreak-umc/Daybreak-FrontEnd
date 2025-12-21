package com.example.daybreak.UI.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.daybreak.Api.ApiClient
import com.example.daybreak.Api.RetrofitClient
import com.example.daybreak.Data.dto.SetupRequest
import com.example.daybreak.GoalFlowActivity
import com.example.daybreak.GoalFlowLoadingActivity
import com.example.daybreak.R
import com.example.daybreak.databinding.FragmentGoalFlowStep2Binding
import kotlinx.coroutines.launch

class GoalFlowStep2Fragment : Fragment() {

    private var _binding: FragmentGoalFlowStep2Binding? = null
    private val binding get() = _binding!!

    private var category: String = "건강"

    private val MAX_LENGTH = 80

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

        category = arguments?.getString("category") ?: "건강"


        initTitle()
        initEditText()
        initButtons()
    }

    /** 🔹 상단 타이틀 */
    override fun onResume() {
        super.onResume()
        (activity as? GoalFlowActivity)
            ?.setTitleText("미래의 내 모습 정의")
    }

    /** 🔹 본문 타이틀 강조 */
    private fun initTitle() {
        val fullText = "${category} 카테고리 안에서\n미래의 내 모습을\n정의해주세요"
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

    /** 🔹 입력창 + 글자 수 제한 처리 */
    private fun initEditText() {
        binding.tvCount.text = "0/80"

        binding.etFutureMe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.toString() ?: ""

                // 🔥 80자 초과 입력 차단
                if (text.length > MAX_LENGTH) {
                    binding.etFutureMe.setText(text.substring(0, MAX_LENGTH))
                    binding.etFutureMe.setSelection(MAX_LENGTH)
                    return
                }

                binding.tvCount.text = "${text.length}/80"

                // 🔥 공백 제외 1자 이상일 때만 활성화
                setGoalCreateButtonEnabled(text.trim().isNotEmpty())

            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setGoalCreateButtonEnabled(enabled: Boolean) {
        val btn = binding.btnGoalCreate.btnPrimary

        if (enabled) {
            // 활성: Primary_500
            btn.isEnabled = true
            btn.backgroundTintList =
                ColorStateList.valueOf(requireContext().getColor(R.color.Primary_500))
            btn.setTextColor(requireContext().getColor(R.color.Gray_0))
        } else {
            // 비활성: 회색
            btn.isEnabled = false
            btn.backgroundTintList =
                ColorStateList.valueOf(requireContext().getColor(R.color.Gray_300))
            btn.setTextColor(requireContext().getColor(R.color.Gray_0))

        }
    }


    /** 🔹 버튼 초기화 */
    private fun initButtons() {

        binding.btnRandomGoal.btnPrimary.apply {
            text = "랜덤 목표 추천 받기"

            // 🔥 배경 투명 고정
            backgroundTintList = ColorStateList.valueOf(
                requireContext().getColor(android.R.color.transparent)
            )

            setTextColor(requireContext().getColor(R.color.Gray_0))


            isEnabled = true

            binding.btnRandomGoal.btnPrimary.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val res = ApiClient.api.getRandomGoal()
                    if (res.isSuccessful && res.body() != null) {
                        val futureMe = res.body()!!.futureMe
                        binding.etFutureMe.setText(futureMe)
                        binding.etFutureMe.setSelection(futureMe.length)
                    } else {
                        // 실패 처리 (토스트 등)
                    }
                }
            }


        }


        // 목표 설정하기 버튼 (활성/비활성 색상 변경 ⭕)
        binding.btnGoalCreate.btnPrimary.apply {
            text = "목표 설정하기"
            setOnClickListener {
                submitGoal()
            }
        }

        // 🔥 처음에는 비활성 상태
        setGoalCreateButtonEnabled(false)
    }

    /** 🔹 목표 생성 */
    private fun submitGoal() {
        val futureMeText = binding.etFutureMe.text.toString().trim()

        viewLifecycleOwner.lifecycleScope.launch {
            val body = SetupRequest(
                category = category,
                futureMe = futureMeText
            )

            val res = ApiClient.api.setupUser(body)
            if (res.isSuccessful) {
                val intent = Intent(requireContext(), GoalFlowLoadingActivity::class.java)
                startActivity(intent)
            } else {
                // 실패 처리
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
