package com.example.daybreak.UI.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.daybreak.Api.AuthViewModel
import com.example.daybreak.R
import com.example.daybreak.ToastDialogFragment
import com.example.daybreak.UI.main.MainActivity
import com.example.daybreak.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val showToast = intent.getBooleanExtra("showLogoutToast", false)
        if (showToast) {
            val toast = ToastDialogFragment("로그아웃 되었습니다", R.drawable.ic_progress_done_32)
            toast.show(supportFragmentManager, "LogoutToast")
        }

        setupObservers()
        setupFocusListeners()
        setupClickListeners()
        setopTextWatchers()
    }

    private fun setupObservers(){
        // 로그인 버튼 활성화 관찰
        viewModel.isLoginEnabled.observe(this){ isEnabled->
            binding.loginLoginBtn.isEnabled = isEnabled
        }
        // 로그인 결과 관찰
        viewModel.loginResult.observe(this){ response ->
            if (response != null && (response.isSuccess || response.code == "COMMON_200")){

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("showLoginToast", true)

                startActivity(intent)
                finish()
            } else{
                showCustomToast("아이디 또는 비밀번호가 틀려요", R.drawable.ic_progress_warning_32)            }
        }
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
            showCustomToast("로그인을 처리 중이에요", R.drawable.ic_progress_progress_32)
            viewModel.login()
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

                viewModel.email.value = binding.loginIdEt.text.toString()
                viewModel.password.value = binding.loginPwdEt.text.toString()
                viewModel.onTextChanged() // 상태 체크
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.loginIdEt.addTextChangedListener(watcher)
        binding.loginPwdEt.addTextChangedListener(watcher)
    }

    private fun showCustomToast(message:String, iconId:Int){
        val toast = ToastDialogFragment(message, iconId)
        toast.show(supportFragmentManager,"CustomToast")
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}