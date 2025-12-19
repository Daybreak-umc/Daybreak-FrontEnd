package com.example.daybreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.daybreak.databinding.FragmentPopUpDialogBinding

class PopUpDialogFragment : DialogFragment() {
    private var _binding: FragmentPopUpDialogBinding? = null
    private val binding get() = _binding!!

    // 클릭 리스너를 저장하는 변수
    private var confirmAction:(() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopUpDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        }

        fun setOnConfirmClickListener(action: () -> Unit) {
            this.confirmAction = action
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}