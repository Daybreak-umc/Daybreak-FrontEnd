package com.example.daybreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.daybreak.databinding.FragmentPopUpDialogBinding

class PopUpDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentPopUpDialogBinding

    // 클릭 이벤트처리를 위한 인터페이스
    interface MyDialogCallback {
        fun onConfirm()
    }
    private var callback: MyDialogCallback? = null

    fun setCallback(callback: MyDialogCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPopUpDialogBinding.inflate(inflater, container, false)

        binding.dialogTitle1.text = arguments?.getString("title") ?: "제목 없음"
        binding.dialogTitle2.text = arguments?.getString("subtitle") ?: ""
        binding.dialogButton2.text = arguments?.getString("btntext") ?: "확인"

        binding.dialogButton1.setOnClickListener {
            dismiss()
        }

        binding.dialogButton2.setOnClickListener {
            callback?.onConfirm()
            dismiss()
        }

        return binding.root
    }

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogTitle1.text = arguments?.getString("title")
        binding.dialogTitle2.text = arguments?.getString("subtitle")
        binding.dialogButton2.text = arguments?.getString("btntext")
        //취소버튼
        binding.dialogButton1.setOnClickListener { //dismiss() }
            // 로그아웃/탈퇴 버튼
            binding.dialogButton2.setOnClickListener {
                confirmAction?.invoke() //
                dismiss()
            }
        }*/

/*        fun setOnConfirmClickListener(action: () -> Unit) {
            this.confirmAction = action
        }
    }*/

/*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
*/

    override fun onStart() {
        super.onStart()
        // 다이얼로그의 창의 배경을 투명하게 해주는것
        dialog?.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

}