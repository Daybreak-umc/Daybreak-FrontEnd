package com.example.daybreak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.daybreak.Api.ApiClient
import com.example.daybreak.Api.RetrofitClient
import com.example.daybreak.databinding.ActivityGoalFlowLoadingBinding
import kotlinx.coroutines.launch

class GoalFlowLoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoalFlowLoadingBinding

    // 로딩 심볼 drawable 목록
    private val symbols = arrayOf(
        R.drawable.img_symbols_first,
        R.drawable.img_symbols_second,
        R.drawable.img_symbols_third,
        R.drawable.img_symbols_fourth
    )

    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    // 2초마다 심볼 변경 Runnable
    private val symbolRunnable = object : Runnable {
        override fun run() {
            currentIndex = (currentIndex + 1) % symbols.size
            binding.ivLoadingSymbol.setImageResource(symbols[currentIndex])

            handler.postDelayed(this, 2000L)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalFlowLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 완전 차단
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 아무 동작도 하지 않음
            }
        })

        // 로딩 애니메이션 시작
        handler.postDelayed(symbolRunnable, 2000L)

        lifecycleScope.launch {
            val res = ApiClient.api.createWeeklyMissions()
            if (res.isSuccessful) {
                // TODO: 로딩 완료 후 다음 화면 이동
            } else {
                // 실패 처리
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(symbolRunnable)
    }
}