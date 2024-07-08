package com.example.loginapi.repositories

import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.LoginRequestDTO
import com.example.loginapi.models.dto.LoginResponseDTO
import com.example.loginapi.models.dto.RegisterRequestDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    fun doLogin(
        email: String,
        password: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.login(LoginRequestDTO(email, password)).enqueue(object : Callback<LoginResponseDTO> {
            override fun onResponse(res: Call<LoginResponseDTO>, response: Response<LoginResponseDTO>) {
                if (response.code() == 401) {
                    success(null)
                    return
                }
                val loginResponse = response.body()
                success(loginResponse)
            }

            override fun onFailure(res: Call<LoginResponseDTO>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun registerUser(
        name: String,
        email: String,
        password: String,
        phone: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        val registerRequest = RegisterRequestDTO(name, email, password, phone)
        service.registerUser(registerRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Error en el registro"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
