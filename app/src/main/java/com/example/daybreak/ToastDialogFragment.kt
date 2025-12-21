package com.example.daybreak

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.daybreak.databinding.FragmentToastDialogBinding

class ToastDialogFragment(private val message: String, private val iconResId: Int) : DialogFragment() {
    private lateinit var binding: FragmentToastDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentToastDialogBinding.inflate(inflater,container,false)

        binding.dialogToastTv.text = message
        binding.dialogToastIv.setImageResource(iconResId)

        return binding.root
    }

    override fun onStart(){
        super.onStart()

        //배경 투명화 및 위치 조절
        dialog?.window?.apply {
            // 배경 어둡게 만드는 딤 효과 제거
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            // 토스트 메시지 떠있어도 버튼 클릭 가능하게
            addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 왼쪽으로 쏠림현상을 해결함
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            //화면 위에 띄게
            setGravity(Gravity.TOP)
            //위 마진 조절
            val params = attributes
            params.y = 100
            params.windowAnimations = android.R.style.Animation_Toast
            attributes = params
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if(isAdded) dismiss()
        }, 3000)
    }
}