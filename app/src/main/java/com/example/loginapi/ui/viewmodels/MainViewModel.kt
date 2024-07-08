package com.example.loginapi.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.repositories.UserRepository

class MainViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>(null)
    }
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun login(email: String, password: String, context: Context) {
        UserRepository.doLogin(email, password, success = {
            if (it == null) {
                _errorMessage.value = "Usuario o contraseña incorrectos"
                return@doLogin
            }
            _errorMessage.value = ""
            val token: String = it.access_token!!
            PreferencesRepository.saveToken(token, context)
        }, failure = {
            _errorMessage.value = "Error de red: ${it.message}"
            it.printStackTrace()
        })
    }
}
