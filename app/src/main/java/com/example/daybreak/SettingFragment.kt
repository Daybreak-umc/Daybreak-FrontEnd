package com.example.daybreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daybreak.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater,container,false)

        binding.settingLogoutTv.setOnClickListener {
            val dialog = PopUpDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("title", "계정에서 로그아웃 하시겠어요?")
                    putString("subtitle","언제든지 다시 돌아올 수 있어요.")
                    putString("btntext","로그아웃")
                }
            }


            dialog.show(childFragmentManager,"Dialogtag")
        }

        binding.settingDropOutTv.setOnClickListener {
            val dialog = PopUpDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("title", "계정을 삭제하시는 건가요?")
                    putString("subtitle", "한번 삭제된 계정은 되돌릴 수 없어요.")
                    putString("btntext", "그래도 탈퇴")
                }
            }

            dialog.show(childFragmentManager, "Dialogtag")
        }

        return binding.root
    }

}