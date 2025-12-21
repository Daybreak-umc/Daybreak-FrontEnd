package com.example.daybreak.Api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daybreak.Data.Login
import com.example.daybreak.Data.LoginResponse
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository(ApiClient.retrofit.create(AuthRetrofitInterface::class.java))

    // UI 상태 관리
    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    private val _isLoginEnabled = MutableLiveData<Boolean>(false)
    val isLoginEnabled: LiveData<Boolean> get() = _isLoginEnabled

    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> get() = _loginResult

    // 입력값 변경 감지
    fun onTextChanged() {
        _isLoginEnabled.value = !email.value.isNullOrBlank() && !password.value.isNullOrBlank()
    }

    // 로그인 실행
    fun login() {
        viewModelScope.launch {
            try {
                val request = Login(email.value ?: "", password.value ?: "")
                val response = repository.login(request)

                if (response.isSuccessful) {
                    _loginResult.postValue(response.body())
                } else {
                    _loginResult.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginResult.postValue(null)
            }
        }
    }
}