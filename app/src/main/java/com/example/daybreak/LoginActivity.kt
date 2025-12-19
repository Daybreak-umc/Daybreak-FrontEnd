package com.example.daybreak

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.daybreak.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFocusListeners()
        setupClickListeners()
        setopTextWatchers()
    }
    //비번 보이는 상태인지 저장하는 함수
    private var isPasswordVisible = false
    private fun setupFocusListeners(){
        //id입력창
        binding.loginIdEt.setOnFocusChangeListener{_, hasFocus->
            if(hasFocus){
                binding.loginIdIv.visibility = View.VISIBLE
            } else{
                binding.loginIdIv.visibility = View.GONE
            }
        }

        binding.loginPwdEt.setOnFocusChangeListener{_, hasFocus->
            if (hasFocus) {
                // 포커스 받으면 현재 상태에 맞는 아이콘 하나만 표시
                updatePasswordIcon()
            } else {
                binding.loginPwdEyeIv.visibility = View.GONE
                binding.loginPwdCloseEyeIv.visibility = View.GONE
            }
        }
    }

    private fun setupClickListeners(){
        val toggleAction = View.OnClickListener{
            isPasswordVisible = !isPasswordVisible //상태 반전

            if(isPasswordVisible){
                // 비번 보이기
                binding.loginPwdEt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // 비번 가리기
                binding.loginPwdEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            //비밀번호 모드를 바꾸면 커서가 맨앞으로 가버리는걸 뒤로 보내버리기
            binding.loginPwdEt.setSelection(binding.loginPwdEt.text.length)

            updatePasswordIcon()
        }

        binding.loginPwdEyeIv.setOnClickListener(toggleAction)
        binding.loginPwdCloseEyeIv.setOnClickListener(toggleAction)

        binding.loginLoginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //  상태에 따라 아이콘 보이거나 없어지는거
    private fun updatePasswordIcon() {
        if (isPasswordVisible) {
            binding.loginPwdEyeIv.visibility = View.VISIBLE
            binding.loginPwdCloseEyeIv.visibility = View.GONE
        } else {
            binding.loginPwdEyeIv.visibility = View.GONE
            binding.loginPwdCloseEyeIv.visibility = View.VISIBLE
        }
    }

    private fun setopTextWatchers(){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 아이디와 비밀번호가 모두 입력되었는지 확인
                val idText = binding.loginIdEt.text.toString()
                val pwdText = binding.loginPwdEt.text.toString()

                // 둘 다 비어있지 않으면 버튼 활성화
                val isEnabled = idText.isNotEmpty() && pwdText.isNotEmpty()
                binding.loginLoginBtn.isEnabled = isEnabled // XML의 버튼 ID가 loginBtn이라 가정
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.loginIdEt.addTextChangedListener(watcher)
        binding.loginPwdEt.addTextChangedListener(watcher)
    }
}