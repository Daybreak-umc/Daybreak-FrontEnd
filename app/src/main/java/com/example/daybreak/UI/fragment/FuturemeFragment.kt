package com.example.daybreak.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daybreak.UI.main.MainActivity
import com.example.daybreak.databinding.FragmentFuturemeBinding

class FuturemeFragment : Fragment() {
    private var _binding: FragmentFuturemeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 2. 바인딩 초기화
        _binding = FragmentFuturemeBinding.inflate(inflater, container, false)
        // 3. MainActivity의 변수 가져오기
        val isGenerated = (activity as? MainActivity)?.isGenerated ?: false
        // 4. 상태에 따른 뷰 제어
        if (isGenerated) {
            binding.layoutFuturemePre.root.visibility = View.GONE
            binding.layoutFuturemePost.root.visibility = View.VISIBLE
        } else {
            binding.layoutFuturemePre.root.visibility = View.VISIBLE
            binding.layoutFuturemePost.root.visibility = View.GONE

            // "미래의 나 생성하기" 버튼 클릭 시 이동 로직
            binding.layoutFuturemePre.btnCreateFutureMc.setOnClickListener {
                // 부모 액티비티인 MainActivity의 함수 호출
                (activity as? MainActivity)?.moveToStep1()
            }

        }

        return binding.root
    }
}