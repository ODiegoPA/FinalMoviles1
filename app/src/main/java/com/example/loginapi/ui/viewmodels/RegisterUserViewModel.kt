package com.example.loginapi.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.LoginResponseDTO
import com.example.loginapi.repositories.UserRepository

class RegisterUserViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>(null)
    }
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _loginResponse: MutableLiveData<LoginResponseDTO?> by lazy {
        MutableLiveData<LoginResponseDTO?>(null)
    }
    val loginResponse: LiveData<LoginResponseDTO?> get() = _loginResponse

    fun registerUser(name: String, email: String, password: String, phone: String, context: Context) {
        UserRepository.registerUser(name, email, password, phone,
            success = {
                UserRepository.doLogin(email, password,
                    success = { loginResponse ->
                        _loginResponse.value = loginResponse
                        _errorMessage.value = null
                    },
                    failure = {
                        _errorMessage.value = "Error en el inicio de sesión: ${it.message}"
                        _loginResponse.value = null
                    }
                )
            },
            failure = {
                _errorMessage.value = "Error en el registro: ${it.message}"
                _loginResponse.value = null
            })
    }
}
