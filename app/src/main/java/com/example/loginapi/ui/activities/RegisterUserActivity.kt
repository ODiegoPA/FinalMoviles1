package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityRegisterUserBinding
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.RegisterUserViewModel

class RegisterUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterUserBinding
    val model: RegisterUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupViewModelObservers()
    }
    private fun setupEventListeners() {
        binding.btnRegisterAddUser.setOnClickListener {
            val name = binding.txtRegisterName.text.toString()
            val email = binding.txtRegisterEmail.text.toString()
            val password = binding.txtRegisterPassword.text.toString()
            val phone = binding.txtRegisterPhone.text.toString()
            model.registerUser(name, email, password, phone, this)
        }
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        model.loginResponse.observe(this) {
            it?.let {
                // Guardar el token y redirigir a MainActivity
                PreferencesRepository.saveToken(it.access_token!!, this)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}